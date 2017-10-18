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

// Originally from UpdatedComponentTests/StandardTesting/REST/Rest_Post_PostBody_String_Blank.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardtesting.rest;

import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import uk.co.exemel.testing.utils.JSONHelpers;
import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolRequestTypeEnum;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;
import uk.co.exemel.testing.utils.disco.manager.RequestLogRequirement;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure that when a Rest (XML/JSON) Post operation is performed against Disco, requiring  a String post body element, that an empty string is accepted
 */
public class RestPostPostBodyStringBlankTest {
    @Test
    public void doTest() throws Exception {
        // Set up the Http Call Bean to make the request
        DiscoManager discoManager1 = DiscoManager.getInstance();
        HttpCallBean getNewHttpCallBean1 = discoManager1.getNewHttpCallBean("87.248.113.14");
        discoManager1 = discoManager1;
        
        getNewHttpCallBean1.setOperationName("mandatoryParamsOperation");
        
        getNewHttpCallBean1.setServiceName("baseline", "discoBaseline");
        
        getNewHttpCallBean1.setVersion("v2");
        
        Map map2 = new HashMap();
        map2.put("HeaderParam","rewqr");
        getNewHttpCallBean1.setHeaderParams(map2);
        
        Map map3 = new HashMap();
        map3.put("queryParam","trete");
        getNewHttpCallBean1.setQueryParams(map3);
        // Set the post body for the request, setting one of the string parameters to a blank string (bodyParameter1)
        Map map4 = new HashMap();
        map4.put("RESTXML","<MandatoryParamsOperationRequest xmlns=\"http://www.betfair.com/servicetypes/v2/Baseline/\"><message><bodyParameter1></bodyParameter1><bodyParameter2>dsdasds</bodyParameter2></message></MandatoryParamsOperationRequest>");
        map4.put("RESTJSON","{\"message\":{\"bodyParameter1\":\"\",\"bodyParameter2\":\"dsdasds\"}}");
        getNewHttpCallBean1.setPostQueryObjects(map4);
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp9 = new Timestamp(System.currentTimeMillis());
        // Make the 4 REST calls to the operation
        discoManager1.makeRestDiscoHTTPCalls(getNewHttpCallBean1);
        // Create the expected response as an XML document (expect bodyParameter1 to be a blank string)
        XMLHelpers xMLHelpers6 = new XMLHelpers();
        Document createAsDocument11 = xMLHelpers6.getXMLObjectFromString("<MandatoryParamsOperationResponseObject><bodyParameter1></bodyParameter1><bodyParameter2>dsdasds</bodyParameter2><headerParameter>rewqr</headerParameter><queryParameter>trete</queryParameter></MandatoryParamsOperationResponseObject>");
        // Create the expected response as a JSON object (expect bodyParameter1 to be a blank string)
        JSONHelpers jSONHelpers7 = new JSONHelpers();
        JSONObject parseJSONObjectFromJSONString12 = jSONHelpers7.parseJSONObjectFromJSONString("{bodyParameter1:\"\",bodyParameter2:\"dsdasds\",headerParameter:\"rewqr\",queryParameter:\"trete\"}");
        // Convert the expected response to REST types for comparison with actual responses
        Map<DiscoMessageProtocolRequestTypeEnum, Object> convertResponseToRestTypes13 = discoManager1.convertResponseToRestTypes(createAsDocument11, getNewHttpCallBean1);
        // Check the 4 responses are as expected
        HttpResponseBean response8 = getNewHttpCallBean1.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLXML);
        AssertionUtils.multiAssertEquals(convertResponseToRestTypes13.get(DiscoMessageProtocolRequestTypeEnum.RESTXML), response8.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response8.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response8.getHttpStatusText());
        
        HttpResponseBean response9 = getNewHttpCallBean1.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(parseJSONObjectFromJSONString12, response9.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response9.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response9.getHttpStatusText());
        
        HttpResponseBean response10 = getNewHttpCallBean1.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLJSON);
        AssertionUtils.multiAssertEquals(parseJSONObjectFromJSONString12, response10.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response10.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response10.getHttpStatusText());
        
        HttpResponseBean response11 = getNewHttpCallBean1.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(convertResponseToRestTypes13.get(DiscoMessageProtocolRequestTypeEnum.RESTXML), response11.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response11.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response11.getHttpStatusText());
        
        // generalHelpers.pauseTest(500L);
        // Check the log entries are as expected
        
        discoManager1.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp9, new RequestLogRequirement("2.8", "mandatoryParamsOperation"),new RequestLogRequirement("2.8", "mandatoryParamsOperation"),new RequestLogRequirement("2.8", "mandatoryParamsOperation"),new RequestLogRequirement("2.8", "mandatoryParamsOperation") );
    }

}
