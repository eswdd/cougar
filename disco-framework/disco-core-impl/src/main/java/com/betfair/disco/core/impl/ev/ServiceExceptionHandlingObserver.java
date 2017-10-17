/*
 * Copyright 2014, Simon Matić Langford
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

package com.betfair.cougar.core.impl.ev;

import com.betfair.cougar.core.api.ev.ExecutionObserver;
import com.betfair.cougar.core.api.ev.ExecutionResult;
import com.betfair.cougar.core.api.exception.CougarClientException;
import com.betfair.cougar.core.api.exception.CougarException;
import com.betfair.cougar.core.api.exception.CougarServiceException;
import com.betfair.cougar.core.api.exception.ServerFaultCode;

/**
 *
 */
public class ServiceExceptionHandlingObserver implements ExecutionObserver {

    private ExecutionObserver wrapped;

    public ServiceExceptionHandlingObserver(ExecutionObserver wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void onResult(ExecutionResult executionResult) {
        if (!executionResult.isFault()) {
            wrapped.onResult(executionResult);
            return;
        }

        CougarException ce = executionResult.getFault();
        if (ce instanceof CougarClientException) {
            wrapped.onResult(new ExecutionResult(new CougarServiceException(ServerFaultCode.ServiceRuntimeException,"Unhandled client exception",ce)));
        }
        else {
            wrapped.onResult(executionResult);
        }
    }
}
