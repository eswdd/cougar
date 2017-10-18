/*
 * Copyright 2013, The Sporting Exchange Limited
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

package uk.co.exemel.testing.utils.disco.assertions;


import uk.co.exemel.testing.utils.disco.misc.AggregatedStepExpectedOutputMetaData;

public interface IAssertion {

	//public abstract Object preProcess(TestStepBean bean) throws JETTFailFastException;

	public abstract Object preProcess(Object actuaObject, AggregatedStepExpectedOutputMetaData expectedObjectMetaData) throws AssertionError;
	public abstract void execute(String message, Object expectedObject, Object actualObject, AggregatedStepExpectedOutputMetaData expectedMetaData) throws AssertionError;

}
