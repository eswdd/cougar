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

// Originally from ClientTests/Transport/StandardTesting/Client_Rescript_Post_RequestTypes_SimpleType_Enums.xls;
package uk.co.exemel.disco.tests.clienttests.standardtesting;

import com.betfair.baseline.v2.BaselineSyncClient;
import com.betfair.baseline.v2.to.EnumSimpleRequestObject;
import com.betfair.baseline.v2.to.EnumSimpleResponseObject;
import uk.co.exemel.disco.api.ExecutionContext;
import uk.co.exemel.disco.tests.clienttests.ClientTestsHelper;
import uk.co.exemel.disco.tests.clienttests.DiscoClientWrapper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Ensure that when a SimpleType ENUM object is passed in parameters to disco via a disco client, the request is sent and the response is handled correctly
 */
public class ClientPostRequestTypesSimpleTypeEnumsTest {
    @Test(dataProvider = "TransportType")
    public void doTest(DiscoClientWrapper.TransportType tt) throws Exception {
        // Set up the client to use rescript transport
        DiscoClientWrapper discoClientWrapper1 = DiscoClientWrapper.getInstance(tt);
        DiscoClientWrapper wrapper = discoClientWrapper1;
        BaselineSyncClient client = discoClientWrapper1.getClient();
        ExecutionContext context = discoClientWrapper1.getCtx();
        // Create body parameter to be passed
        EnumSimpleRequestObject enumSimpleRequestObject2 = new EnumSimpleRequestObject();
        enumSimpleRequestObject2.setBodyParameter(com.betfair.baseline.v2.enumerations.SimpleEnum.FOO);
        EnumSimpleRequestObject bodyParam = enumSimpleRequestObject2;
        // Make call to the method via client and validate response is as expected
        EnumSimpleResponseObject response3 = client.enumSimpleOperation(context, com.betfair.baseline.v2.enumerations.SimpleEnum.FOOBAR, com.betfair.baseline.v2.enumerations.SimpleEnum.BAR, bodyParam);
        assertEquals(com.betfair.baseline.v2.enumerations.SimpleEnum.BAR, response3.getQueryParameter());
        assertEquals(com.betfair.baseline.v2.enumerations.SimpleEnum.FOOBAR, response3.getHeaderParameter());
        assertEquals(com.betfair.baseline.v2.enumerations.SimpleEnum.FOO, response3.getBodyParameter());
    }

    @DataProvider(name="TransportType")
    public Object[][] clients() {
        return ClientTestsHelper.clientsToTest();
    }

}
