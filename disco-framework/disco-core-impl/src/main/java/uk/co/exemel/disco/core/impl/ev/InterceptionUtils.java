/*
 * Copyright 2014, The Sporting Exchange Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.exemel.disco.core.impl.ev;

import uk.co.exemel.disco.api.ExecutionContext;
import uk.co.exemel.disco.api.fault.DiscoApplicationException;
import uk.co.exemel.disco.core.api.ev.ExecutionObserver;
import uk.co.exemel.disco.core.api.ev.ExecutionPreProcessor;
import uk.co.exemel.disco.core.api.ev.ExecutionRequirement;
import uk.co.exemel.disco.core.api.ev.ExecutionResult;
import uk.co.exemel.disco.core.api.ev.InterceptorResult;
import uk.co.exemel.disco.core.api.ev.InterceptorState;
import uk.co.exemel.disco.core.api.ev.OperationKey;
import uk.co.exemel.disco.core.api.exception.DiscoException;
import uk.co.exemel.disco.core.api.exception.DiscoFrameworkException;
import uk.co.exemel.disco.core.api.exception.ServerFaultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class InterceptionUtils {

    private static final InterceptorResult CONTINUE = new InterceptorResult(InterceptorState.CONTINUE);
    private final static Logger LOGGER = LoggerFactory.getLogger(InterceptingExecutableWrapper.class);

    public static void execute(List<ExecutionPreProcessor> preExecutionInterceptorList, List<ExecutionPreProcessor> unexecutedPreExecutionInterceptorList, ExecutionRequirement phase, Runnable executionBody, ExecutionContext ctx, OperationKey key, Object[] args,
                        ExecutionObserver observer) {

        InterceptorResult result = invokePreProcessingInterceptors(preExecutionInterceptorList, unexecutedPreExecutionInterceptorList, phase, ctx, key, args);

        /**
         * Pre-processors can force ON_EXCEPTION or ON_RESULT without execution.
         * The shouldInvoke will indicate whether actual invocation should take place.
         */
        if (result.getState().shouldInvoke()) {
            executionBody.run();
        } else {
            if (InterceptorState.FORCE_ON_EXCEPTION.equals(result.getState())) {
                Object interceptorResult = result.getResult();
                ExecutionResult executionResult;
                if (interceptorResult instanceof DiscoException) {
                    executionResult = new ExecutionResult(interceptorResult);
                } else if (interceptorResult instanceof DiscoApplicationException) {
                    executionResult = new ExecutionResult(interceptorResult);
                } else if (result.getResult() instanceof Exception) {
                    executionResult = new ExecutionResult(
                            new DiscoFrameworkException(ServerFaultCode.ServiceRuntimeException,
                                    "Interceptor forced exception", (Exception)result.getResult()));
                } else {
                    // onException forced, but result is not an exception
                    executionResult = new ExecutionResult(
                            new DiscoFrameworkException(ServerFaultCode.ServiceRuntimeException,
                                    "Interceptor forced exception, but result was not an exception - I found a " +
                                            result.getResult()));
                }
                observer.onResult(executionResult);

            } else if (InterceptorState.FORCE_ON_RESULT.equals(result.getState())) {
                observer.onResult(new ExecutionResult(result.getResult()));
            }
        }
    }


    private static InterceptorResult invokePreProcessingInterceptors(List<ExecutionPreProcessor> preExecutionInterceptorList, List<ExecutionPreProcessor> unexecutedPreExecutionInterceptorList, ExecutionRequirement phase, ExecutionContext ctx, OperationKey key, Object[] args) {
        InterceptorResult result = CONTINUE;

        List<ExecutionPreProcessor> toIterateOver = new ArrayList<>(preExecutionInterceptorList);
        for (ExecutionPreProcessor pre : toIterateOver) {
            ExecutionRequirement req = pre.getExecutionRequirement();
            if (req == ExecutionRequirement.EVERY_OPPORTUNITY || req == phase || (req == ExecutionRequirement.EXACTLY_ONCE && unexecutedPreExecutionInterceptorList.contains(pre))) {
                try {
                    result = pre.invoke(ctx, key, args);
                    unexecutedPreExecutionInterceptorList.remove(pre);
                    if (result == null || result.getState() == null) {
                        // defensive
                        throw new IllegalStateException(pre.getName() +" did not return a valid InterceptorResult");
                    }
                } catch (Exception e) {
                    LOGGER.error("Pre Processor " + pre.getName() + " has failed.", e);
                    result = new InterceptorResult(InterceptorState.FORCE_ON_EXCEPTION, e);
                    break;
                }
                if (result.getState().shouldAbortInterceptorChain()) {
                    break;
                }
            }
        }
        return result;
    }
}
