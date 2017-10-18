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

// Originally from UpdatedComponentTests/StandardTesting/REST/Rest_Post_RequestTypes_String_QueryParam_Blank.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardtesting.rest;

import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import uk.co.exemel.testing.utils.JSONHelpers;
import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;
import uk.co.exemel.testing.utils.disco.manager.RequestLogRequirement;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure that when a request for an operation taking a String Query Param is passesd a blank String, the operation returns correctly
 */
public class RestPostRequestTypesStringQueryParamBlankTest {
    @Test
    public void doTest() throws Exception {
        // Set up the Http Call Bean to make the request
        DiscoManager discoManager1 = DiscoManager.getInstance();
        HttpCallBean getNewHttpCallBean1 = discoManager1.getNewHttpCallBean();
        discoManager1 = discoManager1;
        
        discoManager1.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");
        
        getNewHttpCallBean1.setOperationName("mandatoryParamsOperation");
        
        getNewHttpCallBean1.setServiceName("baseline", "discoBaseline");
        
        getNewHttpCallBean1.setVersion("v2");
        
        Map map2 = new HashMap();
        map2.put("HeaderParam","hgfhhgfhghd");
        getNewHttpCallBean1.setHeaderParams(map2);
        // Set the Query Param to be a blank string
        Map map3 = new HashMap();
        map3.put("queryParam","");
        getNewHttpCallBean1.setQueryParams(map3);
        
        getNewHttpCallBean1.setRestPostQueryObjects(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream("<message><bodyParameter1>gfdgdgfdg</bodyParameter1></message>".getBytes())));
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp10 = new Timestamp(System.currentTimeMillis());
        // Make the 4 REST calls to the operation
        discoManager1.makeRestDiscoHTTPCalls(getNewHttpCallBean1);
        // Create the expected response as an XML document
        XMLHelpers xMLHelpers5 = new XMLHelpers();
        Document createAsDocument12 = xMLHelpers5.getXMLObjectFromString("<MandatoryParamsOperationResponse><MandatoryParamsOperationResponseObject><bodyParameter1>gfdgdgfdg</bodyParameter1><headerParameter>hgfhhgfhghd</headerParameter><queryParameter/></MandatoryParamsOperationResponseObject></MandatoryParamsOperationResponse>");
        // Create the expected response as a JSON object
        JSONHelpers jSONHelpers6 = new JSONHelpers();
        JSONObject createAsJSONObject13 = jSONHelpers6.createAsJSONObject(new JSONObject("{\"bodyParameter1\":\"gfdgdgfdg\",\"headerParameter\":\"hgfhhgfhghd\",\"queryParameter\":\"\"}"));
        // Check the 4 responses are as expected
        HttpResponseBean response7 = getNewHttpCallBean1.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLXML);
        AssertionUtils.multiAssertEquals(createAsDocument12, response7.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response7.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response7.getHttpStatusText());
        
        HttpResponseBean response8 = getNewHttpCallBean1.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(createAsJSONObject13, response8.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response8.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response8.getHttpStatusText());
        
        HttpResponseBean response9 = getNewHttpCallBean1.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLJSON);
        AssertionUtils.multiAssertEquals(createAsJSONObject13, response9.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response9.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response9.getHttpStatusText());
        
        HttpResponseBean response10 = getNewHttpCallBean1.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(createAsDocument12, response10.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response10.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response10.getHttpStatusText());
        // Check the log entries are as expected
        
        discoManager1.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp10, new RequestLogRequirement("2.8", "mandatoryParamsOperation"),new RequestLogRequirement("2.8", "mandatoryParamsOperation"),new RequestLogRequirement("2.8", "mandatoryParamsOperation"),new RequestLogRequirement("2.8", "mandatoryParamsOperation") );
    }

}
