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

// Originally from UpdatedComponentTests/ResponseTypes/RPC/RPC_Post_SetResponse_BlankEntry.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.responsetypes.rpc;

import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageContentTypeEnum;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolRequestTypeEnum;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.helpers.DiscoHelpers;
import uk.co.exemel.testing.utils.disco.manager.AccessLogRequirement;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;
import uk.co.exemel.testing.utils.disco.manager.RequestLogRequirement;

import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure that when a Batched JSON request is performed against Disco, passing a Set with a blank entry in the post body, it is correctly de-serialized, processed, returned the correct response.
 */
public class RPCPostSetResponseBlankEntryTest {
    @Test
    public void doTest() throws Exception {
        // Set up the Http Call Bean to make the request
        DiscoManager discoManager1 = DiscoManager.getInstance();
        HttpCallBean callBean = discoManager1.getNewHttpCallBean("87.248.113.14");
        DiscoManager discoManager = discoManager1;
        // Set the call bean to use JSON batching
        callBean.setJSONRPC(true);
        // Set the list of requests to make a batched call to
        Map[] mapArray2 = new Map[2];
        mapArray2[0] = new HashMap();
        mapArray2[0].put("method","testSimpleSetGet");
        mapArray2[0].put("params","[[\"Value a\",\"\"]]");
        mapArray2[0].put("id","1");
        mapArray2[1] = new HashMap();
        mapArray2[1].put("method","testSimpleSetGet");
        mapArray2[1].put("params","[[\"Value c\",\"\"]]");
        mapArray2[1].put("id","2");
        callBean.setBatchedRequests(mapArray2);
        // Get current time for getting log entries later

        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        // Make JSON call to the operation requesting a JSON response
        discoManager.makeRestDiscoHTTPCall(callBean, DiscoMessageProtocolRequestTypeEnum.RESTJSON, DiscoMessageContentTypeEnum.JSON);
        // Get the response to the batched query (store the response for further comparison as order of batched responses cannot be relied on)
        HttpResponseBean response = callBean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        // Convert the returned json object to a map for comparison
        DiscoHelpers discoHelpers4 = new DiscoHelpers();
        Map<String, Object> map5 = discoHelpers4.convertBatchedResponseToMap(response);
        AssertionUtils.multiAssertEquals("{\"id\":1,\"result\":[\"\",\"Value a\"],\"jsonrpc\":\"2.0\"}", map5.get("response1"));
        AssertionUtils.multiAssertEquals("{\"id\":2,\"result\":[\"\",\"Value c\"],\"jsonrpc\":\"2.0\"}", map5.get("response2"));
        AssertionUtils.multiAssertEquals(200, map5.get("httpStatusCode"));
        AssertionUtils.multiAssertEquals("OK", map5.get("httpStatusText"));
        // Pause the test to allow the logs to be filled
        // generalHelpers.pauseTest(500L);
        // Check the log entries are as expected
        
        discoManager.verifyRequestLogEntriesAfterDate(timeStamp, new RequestLogRequirement("2.8", "testSimpleSetGet"),new RequestLogRequirement("2.8", "testSimpleSetGet") );
        
        discoManager.verifyAccessLogEntriesAfterDate(timeStamp, new AccessLogRequirement("87.248.113.14", "/json-rpc", "Ok") );
    }

}
