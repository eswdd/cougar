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

package uk.co.exemel.disco.core.api.ev;

/**
 * Interface to allow multiple ExecutableResolvers to be registered with one resolver,
 * which will delegate to the registered resolvers, and possibly decorate the executables
 * to allow additional functionality to be plugged in before and after execution.
 *
 */
public interface CompoundExecutableResolver extends ExecutableResolver {

    public void registerExecutableResolver(ExecutableResolver executableResolver);

	public void registerExecutableResolver(String namespace, ExecutableResolver executableResolver);

}
