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

// Originally from UpdatedComponentTests/Concurrency/SOAP/SOAP_ConcurrentRequests_SINGLE_SERVICE_RAPID_LOAD.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.concurrency.soap;

import uk.co.exemel.disco.testing.concurrency.SOAPConcurrencySingleServiceTest;
import uk.co.exemel.disco.testing.concurrency.SOAPConcurrenyResultBean;
import com.betfair.testing.utils.disco.assertions.AssertionUtils;
import com.betfair.testing.utils.disco.beans.HttpResponseBean;
import org.testng.annotations.Test;

import java.util.Map;


/**
 * Ensure that when concurrent SOAP requests are performed against Disco with a rapid load of a large number of threads, each request is successfully sent and the response to each is correctly  handled
 */
public class SOAPConcurrentRequestsSINGLESERVICERAPIDLOADTest {
    @Test
    public void doTest() throws Exception {
        // Execute the test, creating the given number of threads and making the given number of SOAP calls per thread to the testComplexMutator operation
        SOAPConcurrenyResultBean executeTest1 = new SOAPConcurrencySingleServiceTest().executeTest(10, 400, "TestComplexMutator");
        // Get the expected responses to the requests made
        Map<String, HttpResponseBean> getExpectedResponses4 = executeTest1.getExpectedResponses();
        // Check the actual responses against the expected ones (with a date tolerance of 2000ms)
        Long oldTolerance = AssertionUtils.setDateTolerance(2000L);
        try {
            AssertionUtils.multiAssertEquals(getExpectedResponses4, executeTest1.getActualResponses());
        }
        finally {
            AssertionUtils.setDateTolerance(oldTolerance);
        }
    }

}
