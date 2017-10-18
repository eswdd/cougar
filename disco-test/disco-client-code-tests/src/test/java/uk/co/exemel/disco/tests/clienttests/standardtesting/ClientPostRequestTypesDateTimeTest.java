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

// Originally from ClientTests/Transport/StandardTesting/Client_Rescript_Post_RequestTypes_DateTime.xls;
package uk.co.exemel.disco.tests.clienttests.standardtesting;

import com.betfair.baseline.v2.BaselineSyncClient;
import com.betfair.baseline.v2.to.BodyParamDateTimeObject;
import com.betfair.baseline.v2.to.DateTimeOperationResponseObject;
import uk.co.exemel.disco.api.ExecutionContext;
import uk.co.exemel.disco.tests.clienttests.ClientTestsHelper;
import uk.co.exemel.disco.tests.clienttests.DiscoClientResponseTypeUtils;
import uk.co.exemel.disco.tests.clienttests.DiscoClientWrapper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Ensure that when a dateTime object is passed in a body parameter to disco via a disco client the request is sent and the response is handled correctly
 */
public class ClientPostRequestTypesDateTimeTest {
    @Test(dataProvider = "TransportType")
    public void doTest(DiscoClientWrapper.TransportType tt) throws Exception {
        // Set up the client to use rescript transport
        DiscoClientWrapper discoClientWrapper1 = DiscoClientWrapper.getInstance(tt);
        DiscoClientWrapper wrapper = discoClientWrapper1;
        BaselineSyncClient client = discoClientWrapper1.getClient();
        ExecutionContext context = discoClientWrapper1.getCtx();
        // Create date to be passed
        DiscoClientResponseTypeUtils discoClientResponseTypeUtils2 = new DiscoClientResponseTypeUtils();
        Date dateParam = discoClientResponseTypeUtils2.createDateFromString("2009-06-01T13:50:00.0Z");
        // Create body parameter to be passed
        BodyParamDateTimeObject bodyParamDateTimeObject3 = new BodyParamDateTimeObject();
        bodyParamDateTimeObject3.setDateTimeParameter(dateParam);
        BodyParamDateTimeObject bodyParam = bodyParamDateTimeObject3;
        // Make call to the method via client and store the result
        DateTimeOperationResponseObject response5 = client.dateTimeOperation(context, bodyParam);
        Date localTime = response5.getLocalTime();
        Date localTime2 = response5.getLocalTime2();
        // Validate the response is as expected
        assertEquals("2009-06-01T 13:50:00.000Z" ,discoClientResponseTypeUtils2.formatDateToString(localTime));
        assertEquals("2009-06-01T 13:50:00.000Z", discoClientResponseTypeUtils2.formatDateToString(localTime2));
    }

    @DataProvider(name="TransportType")
    public Object[][] clients() {
        return ClientTestsHelper.clientsToTest();
    }

}
