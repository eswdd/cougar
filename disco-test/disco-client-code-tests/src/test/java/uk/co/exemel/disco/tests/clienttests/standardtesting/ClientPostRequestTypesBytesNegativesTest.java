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

// Originally from ClientTests/Transport/StandardTesting/Client_Rescript_Post_RequestTypes_Bytes_Negatives.xls;
package uk.co.exemel.disco.tests.clienttests.standardtesting;

import com.betfair.baseline.v2.BaselineSyncClient;
import com.betfair.baseline.v2.to.BodyParamByteObject;
import com.betfair.baseline.v2.to.ByteOperationResponseObject;
import uk.co.exemel.disco.api.ExecutionContext;
import uk.co.exemel.disco.tests.clienttests.ClientTestsHelper;
import uk.co.exemel.disco.tests.clienttests.DiscoClientResponseTypeUtils;
import uk.co.exemel.disco.tests.clienttests.DiscoClientWrapper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Ensure that when a Negative Byte object is passed in parameters to disco via a disco client, the request is sent and the response is handled correctly
 */
public class ClientPostRequestTypesBytesNegativesTest {
    @Test(dataProvider = "TransportType")
    public void doTest(DiscoClientWrapper.TransportType tt) throws Exception {
        // Set up the client to use rescript transport
        DiscoClientWrapper discoClientWrapper1 = DiscoClientWrapper.getInstance(tt);
        DiscoClientWrapper wrapper = discoClientWrapper1;
        BaselineSyncClient client = discoClientWrapper1.getClient();
        ExecutionContext context = discoClientWrapper1.getCtx();
        // Create body parameter to be passed
        DiscoClientResponseTypeUtils discoClientResponseTypeUtils2 = new DiscoClientResponseTypeUtils();
        BodyParamByteObject bodyParam = discoClientResponseTypeUtils2.buildByteBodyParamObject("-1,1,2,3,4,5");
        // Make call to the method via client (passing negatives) and validate response is as expected
        ByteOperationResponseObject response = client.byteOperation(context, (byte) -128, (byte) -50, bodyParam);
        assertEquals(-50, (byte) response.getQueryParameter());
        assertEquals(-128, (byte) response.getHeaderParameter());
        // Compare the byte array stored in the body parameter to check the inputted version against the returned version
        DiscoClientResponseTypeUtils discoClientResponseTypeUtils4 = new DiscoClientResponseTypeUtils();
        boolean match = discoClientResponseTypeUtils4.compareByteArrays(bodyParam, response);
        assertEquals(true, match);
    }

    @DataProvider(name="TransportType")
    public Object[][] clients() {
        return ClientTestsHelper.clientsToTest();
    }

}
