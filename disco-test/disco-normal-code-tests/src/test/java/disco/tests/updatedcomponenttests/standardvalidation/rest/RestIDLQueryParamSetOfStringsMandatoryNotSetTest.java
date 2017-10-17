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

// Originally from UpdatedComponentTests/StandardValidation/REST/Rest_IDL_QueryParam_SetOfStrings_MandatoryNotSet.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardvalidation.rest;

import com.betfair.testing.utils.disco.misc.XMLHelpers;
import com.betfair.testing.utils.disco.assertions.AssertionUtils;
import com.betfair.testing.utils.disco.beans.HttpCallBean;
import com.betfair.testing.utils.disco.beans.HttpResponseBean;
import com.betfair.testing.utils.disco.enums.DiscoMessageProtocolRequestTypeEnum;
import com.betfair.testing.utils.disco.manager.DiscoManager;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure that Disco returns the correct fault, when a REST request is missing a mandatory Set of Strings Query parameter
 */
public class RestIDLQueryParamSetOfStringsMandatoryNotSetTest {
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
        // Set the parameters, don't set the mandatory set of strings query parameter
        Map map2 = new HashMap();
        map2.put("HeaderParam","header1,headerTwo,header3,header4");
        httpCallBeanBaseline.setHeaderParams(map2);
        // Get current time for getting log entries later

        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        // Make the 4 REST calls to the operation
        discoManagerBaseline.makeRestDiscoHTTPCalls(httpCallBeanBaseline);
        // Create the expected response as an XML document (Fault)
        XMLHelpers xMLHelpers4 = new XMLHelpers();
        Document xmlDocument = xMLHelpers4.getXMLObjectFromString("<fault><faultcode>Client</faultcode><faultstring>DSC-0018</faultstring><detail/></fault>");
        // Convert the expected response to a JSON object for comparison with actual responses
        Map<DiscoMessageProtocolRequestTypeEnum, Object> convertedResponses = discoManagerBaseline.convertResponseToRestTypes(xmlDocument, httpCallBeanBaseline);
        // Check the 4 responses are as expected (Bad Request)
        HttpResponseBean response5 = httpCallBeanBaseline.getResponseObjectsByEnum(com.betfair.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum.RESTXMLXML);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTXML), response5.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 400, response5.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Bad Request", response5.getHttpStatusText());
        
        HttpResponseBean response6 = httpCallBeanBaseline.getResponseObjectsByEnum(com.betfair.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTJSON), response6.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 400, response6.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Bad Request", response6.getHttpStatusText());
        
        HttpResponseBean response7 = httpCallBeanBaseline.getResponseObjectsByEnum(com.betfair.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum.RESTXMLJSON);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTJSON), response7.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 400, response7.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Bad Request", response7.getHttpStatusText());
        
        HttpResponseBean response8 = httpCallBeanBaseline.getResponseObjectsByEnum(com.betfair.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTXML), response8.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 400, response8.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Bad Request", response8.getHttpStatusText());
        // Check the log entries are as expected
        
    }

}
