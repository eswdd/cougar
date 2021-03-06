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

// Originally from UpdatedComponentTests/StandardTesting/REST/Rest_Get_RequestTypes_Parameters_SetOfStrings_EscapedCharacters.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardtesting.rest;

import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolRequestTypeEnum;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;
import uk.co.exemel.testing.utils.disco.manager.RequestLogRequirement;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure that when the 4 supported Rest XML/JSON Gets are performed, Disco can handle a Set of Strings containing escaped characters being passed in the Header and Query parameters
 */
public class RestGetRequestTypesParametersSetOfStringsEscapedCharactersTest {
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
        
        httpCallBeanBaseline.setOperationName("stringSetOperation");
        // Set the parameters to sets of string containing escaped characters
        Map map2 = new HashMap();
        map2.put("HeaderParam","a header1,:header2,this & that is 100%");
        httpCallBeanBaseline.setHeaderParams(map2);
        
        Map map3 = new HashMap();
        map3.put("queryParam","a%20query1,%3Aquery2,this%20%26%20that%20is%20100%25");
        httpCallBeanBaseline.setQueryParams(map3);
        // Get current time for getting log entries later

        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        // Make the 4 REST calls to the operation
        discoManagerBaseline.makeRestDiscoHTTPCalls(httpCallBeanBaseline);
        // Create the expected response as an XML document
        XMLHelpers xMLHelpers5 = new XMLHelpers();
        Document xmlDocument = xMLHelpers5.getXMLObjectFromString("<NonMandatoryParamsOperationResponseObject><headerParameter>:header2,a header1,this &amp; that is 100%</headerParameter><queryParameter>:query2,a query1,this &amp; that is 100%</queryParameter></NonMandatoryParamsOperationResponseObject>");
        // Convert the expected response to REST types for comparison with actual responses
        Map<DiscoMessageProtocolRequestTypeEnum, Object> convertedResponses = discoManagerBaseline.convertResponseToRestTypes(xmlDocument, httpCallBeanBaseline);
        // Check the 4 responses are as expected
        HttpResponseBean response6 = httpCallBeanBaseline.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLXML);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTXML), response6.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response6.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response6.getHttpStatusText());
        
        HttpResponseBean response7 = httpCallBeanBaseline.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTJSON), response7.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response7.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response7.getHttpStatusText());
        
        HttpResponseBean response8 = httpCallBeanBaseline.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLJSON);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTJSON), response8.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response8.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response8.getHttpStatusText());
        
        HttpResponseBean response9 = httpCallBeanBaseline.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTXML), response9.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response9.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response9.getHttpStatusText());
        // Check the log entries are as expected
        
        discoManagerBaseline.verifyRequestLogEntriesAfterDate(timeStamp, new RequestLogRequirement("2.8", "stringSetOperation"),new RequestLogRequirement("2.8", "stringSetOperation"),new RequestLogRequirement("2.8", "stringSetOperation"),new RequestLogRequirement("2.8", "stringSetOperation") );
    }

}
