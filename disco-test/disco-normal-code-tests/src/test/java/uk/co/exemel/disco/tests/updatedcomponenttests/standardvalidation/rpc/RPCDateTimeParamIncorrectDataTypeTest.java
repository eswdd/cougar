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

// Originally from UpdatedComponentTests/StandardValidation/RPC/RPC_DateTimeParam_IncorrectDataType.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardvalidation.rpc;

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
 * Ensure that Disco returns the correct fault when a  RPC request with date/time params is made with incorrect data type parameter
 */
public class RPCDateTimeParamIncorrectDataTypeTest {
    @Test
    public void doTest() throws Exception {
        // Create the HttpCallBean
        DiscoManager discoManager1 = DiscoManager.getInstance();
        HttpCallBean httpCallBeanBaseline = discoManager1.getNewHttpCallBean();
        DiscoManager discoManagerBaseline = discoManager1;
        // Get the disco logging attribute for getting log entries later
        // Point the created HttpCallBean at the correct service
        httpCallBeanBaseline.setServiceName("baseline", "discoBaseline");

        httpCallBeanBaseline.setVersion("v2");
        // Set up the Http Call Bean to make the request
        DiscoManager discoManager2 = DiscoManager.getInstance();
        HttpCallBean callBean = discoManager2.getNewHttpCallBean("87.248.113.14");
        DiscoManager discoManager = discoManager2;

        discoManager.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");
        // Set the call bean to use JSON batching
        callBean.setJSONRPC(true);
        // Set the list of requests to make a batched call to
        Map[] mapArray3 = new Map[7];
        mapArray3[0] = new HashMap();
        mapArray3[0].put("method","dateTimeOperation");
        mapArray3[0].put("params","[{\"dateTimeParameter\":\"2009-12-01T23:00:00.000Z\"}]");
        mapArray3[0].put("id","\"Call with correct params\"");
        mapArray3[1] = new HashMap();
        mapArray3[1].put("method","dateTimeOperation");
        mapArray3[1].put("params","[{\"dateTimeParameter\":\"true\"}]");
        mapArray3[1].put("id","\"Invalid parameter type\"");
        mapArray3[2] = new HashMap();
        mapArray3[2].put("method","dateTimeOperation");
        mapArray3[2].put("params","[{\"dateTimeParameter\":\"2009-12-01T25:00:00.000Z\"}]");
        mapArray3[2].put("id","\"Invalid Hour\"");
        mapArray3[3] = new HashMap();
        mapArray3[3].put("method","dateTimeOperation");
        mapArray3[3].put("params","[{\"dateTimeParameter\":\"2009-02-29T13:50:00.435Z\"}]");
        mapArray3[3].put("id","\"Invalid Leap Year\"");
        mapArray3[4] = new HashMap();
        mapArray3[4].put("method","dateTimeOperation");
        mapArray3[4].put("params","[{\"dateTimeParameter\":\"2009-12-01T23:60:00.000Z\"}]");
        mapArray3[4].put("id","\"Invalid Minute\"");
        mapArray3[5] = new HashMap();
        mapArray3[5].put("method","dateTimeOperation");
        mapArray3[5].put("params","[{\"dateTimeParameter\":\"2009-13-01T23:60:00.000Z\"}]");
        mapArray3[5].put("id","\"Invalid Month\"");
        mapArray3[6] = new HashMap();
        mapArray3[6].put("method","dateTimeOperation");
        mapArray3[6].put("params","[{\"dateTimeParameter\":\"2009-00-01T23:60:00.000Z\"}]");
        mapArray3[6].put("id","\"Invalid Month2\"");
        callBean.setBatchedRequests(mapArray3);
        // Get current time for getting log entries later

        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        // Make JSON call to the operation requesting a JSON response
        discoManager.makeRestDiscoHTTPCall(callBean, DiscoMessageProtocolRequestTypeEnum.RESTJSON, DiscoMessageContentTypeEnum.JSON);
        // Get the response to the batched query (store the response for further comparison as order of batched responses cannot be relied on)
        HttpResponseBean actualResponseJSON = callBean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        // Convert the returned json object to a map for comparison
        DiscoHelpers discoHelpers5 = new DiscoHelpers();
        Map<String, Object> map6 = discoHelpers5.convertBatchedResponseToMap(actualResponseJSON);
        AssertionUtils.multiAssertEquals("{\"id\":\"Call with correct params\",\"result\":{\"localTime\":\"2009-12-01T23:00:00.000Z\",\"localTime2\":\"2009-12-01T23:00:00.000Z\"},\"jsonrpc\":\"2.0\"}", map6.get("responseCall with correct params"));
        AssertionUtils.multiAssertEquals("{\"id\":\"Invalid parameter type\",\"error\":{\"message\":\"DSC-0044\",\"code\":-32602},\"jsonrpc\":\"2.0\"}", map6.get("responseInvalid parameter type"));
        AssertionUtils.multiAssertEquals("{\"id\":\"Invalid Hour\",\"error\":{\"message\":\"DSC-0044\",\"code\":-32602},\"jsonrpc\":\"2.0\"}", map6.get("responseInvalid Hour"));
        AssertionUtils.multiAssertEquals("{\"id\":\"Invalid Minute\",\"error\":{\"message\":\"DSC-0044\",\"code\":-32602},\"jsonrpc\":\"2.0\"}", map6.get("responseInvalid Minute"));
        AssertionUtils.multiAssertEquals("{\"id\":\"Invalid Leap Year\",\"error\":{\"message\":\"DSC-0044\",\"code\":-32602},\"jsonrpc\":\"2.0\"}", map6.get("responseInvalid Leap Year"));
        AssertionUtils.multiAssertEquals("{\"id\":\"Invalid Month\",\"error\":{\"message\":\"DSC-0044\",\"code\":-32602},\"jsonrpc\":\"2.0\"}", map6.get("responseInvalid Month"));
        AssertionUtils.multiAssertEquals("{\"id\":\"Invalid Month2\",\"error\":{\"message\":\"DSC-0044\",\"code\":-32602},\"jsonrpc\":\"2.0\"}", map6.get("responseInvalid Month2"));
        AssertionUtils.multiAssertEquals(200, map6.get("httpStatusCode"));
        AssertionUtils.multiAssertEquals("OK", map6.get("httpStatusText"));
        // Pause the test to allow the logs to be filled
        // generalHelpers.pauseTest(500L);
        // Check the log entries are as expected

        discoManager.verifyRequestLogEntriesAfterDate(timeStamp, new RequestLogRequirement("2.8", "dateTimeOperation") );

        DiscoManager discoManager10 = DiscoManager.getInstance();
        discoManager10.verifyAccessLogEntriesAfterDate(timeStamp, new AccessLogRequirement("87.248.113.14", "/json-rpc", "Ok") );
    }

}
