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

// Originally from UpdatedComponentTests/IPBlacklist/RPC/RPC_BlacklistedIP.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.ipblacklist;

import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageContentTypeEnum;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolRequestTypeEnum;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.helpers.DiscoHelpers;
import uk.co.exemel.testing.utils.disco.manager.AccessLogRequirement;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;

import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Test that the Disco service forbids the access for the X-Forwarded-For  in the blacklist (Batched JSON)
 */
public class RPCBlacklistedIPTest {
    @Test
    public void doTest_Single_IP() throws Exception {
        String testIPAddress = "192.168.0.1";

        doTest_ExpectedResponse(testIPAddress, false);
    }

    @Test
    public void doTest_FirstItem_IPList() throws Exception {
        String testIPAddress = "192.168.0.1;1.2.3.4;5.6.7.8";

        doTest_ExpectedResponse(testIPAddress, false);
    }

    @Test
    public void doTest_MiddleItem_IPList() throws Exception {
        String testIPAddress = "1.2.3.4;192.168.0.1;5.6.7.8";

        doTest_ExpectedResponse(testIPAddress, true);
    }

    @Test
    public void doTest_LastItem_IPList() throws Exception {
        String testIPAddress = "1.2.3.4;5.6.7.8;192.168.0.1";

        doTest_ExpectedResponse(testIPAddress, true);
    }


    private void doTest_ExpectedResponse(String testIPAddress, boolean ok) throws Exception
    {
        // Set up the Http Call Bean to make the request
        DiscoManager discoManager = DiscoManager.getInstance();
        HttpCallBean callBean = discoManager.getNewHttpCallBean(testIPAddress.replace(";",","));
        // Set Disco Fault Controller attributes
        discoManager.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");
        // Set the call bean to use JSON batching
        callBean.setJSONRPC(true);
        // Set the list of requests to make a batched call to
        Map<String, String>[] mapArray2 = new Map[2];
        mapArray2[0] = new HashMap<String, String>();
        mapArray2[0].put("method","stringSimpleTypeEcho");
        mapArray2[0].put("params","[\"foo\"]");
        mapArray2[0].put("id","1");
        mapArray2[1] = new HashMap<String, String>();
        mapArray2[1].put("method","stringSimpleTypeEcho");
        mapArray2[1].put("params","[\"foo\"]");
        mapArray2[1].put("id","2");
        callBean.setBatchedRequests(mapArray2);
        // Get current time for getting log entries later

        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        // Make JSON call to the operation requesting a JSON response
        discoManager.makeRestDiscoHTTPCall(callBean, DiscoMessageProtocolRequestTypeEnum.RESTJSON, DiscoMessageContentTypeEnum.JSON);
        // Get the response to the batched query (store the response for further comparison as order of batched responses cannot be relied on)
        HttpResponseBean actualResponseJSON = callBean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        // Convert the returned json object to a map for comparison
        DiscoHelpers discoHelpers4 = new DiscoHelpers();
        Map<String, Object> map5 = discoHelpers4.convertBatchedResponseToMap(actualResponseJSON);
        if (ok) {
            AssertionUtils.multiAssertEquals("{\"id\":1,\"result\":\"foo\",\"jsonrpc\":\"2.0\"}", map5.get("response1"));
            AssertionUtils.multiAssertEquals("{\"id\":2,\"result\":\"foo\",\"jsonrpc\":\"2.0\"}", map5.get("response2"));
        }
        else {
            AssertionUtils.multiAssertEquals("{\"id\":1,\"error\":{\"message\":\"DSC-0015\",\"code\":-32099},\"jsonrpc\":\"2.0\"}", map5.get("response1"));
            AssertionUtils.multiAssertEquals("{\"id\":2,\"error\":{\"message\":\"DSC-0015\",\"code\":-32099},\"jsonrpc\":\"2.0\"}", map5.get("response2"));
        }
        AssertionUtils.multiAssertEquals("200", map5.get("httpStatusCode"));
        AssertionUtils.multiAssertEquals("OK", map5.get("httpStatusText"));
        // Check the log entries are as expected
        discoManager.verifyAccessLogEntriesAfterDate(timeStamp, new AccessLogRequirement(testIPAddress, "/json-rpc", "Ok"));
    }
}
