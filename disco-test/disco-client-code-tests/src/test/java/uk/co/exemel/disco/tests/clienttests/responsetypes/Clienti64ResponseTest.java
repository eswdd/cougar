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

// Originally from ClientTests/Transport/ResponseTypes/Client_Rescript_i64Response.xls;
package uk.co.exemel.disco.tests.clienttests.responsetypes;

import com.betfair.baseline.v2.BaselineSyncClient;
import uk.co.exemel.disco.api.ExecutionContext;
import uk.co.exemel.disco.tests.clienttests.ClientTestsHelper;
import uk.co.exemel.disco.tests.clienttests.DiscoClientWrapper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Ensure that when a i64SimpleTypeEcho operation is performed against disco via a disco client the request is sent and the response is handled correctly
 */
public class Clienti64ResponseTest {
    @Test(dataProvider = "TransportType")
    public void doTest(DiscoClientWrapper.TransportType tt) throws Exception {
        // Set up and call the method using rescript transport
        DiscoClientWrapper discoClientWrapper1 = DiscoClientWrapper.getInstance(tt);
        DiscoClientWrapper wrapper = discoClientWrapper1;
        BaselineSyncClient client = discoClientWrapper1.getClient();
        ExecutionContext context = discoClientWrapper1.getCtx();
        
        long msg = client.i64SimpleTypeEcho(context, 1000L);
        assertEquals(1000L, msg);
    }

    @DataProvider(name="TransportType")
    public Object[][] clients() {
        return ClientTestsHelper.clientsToTest();
    }

}
