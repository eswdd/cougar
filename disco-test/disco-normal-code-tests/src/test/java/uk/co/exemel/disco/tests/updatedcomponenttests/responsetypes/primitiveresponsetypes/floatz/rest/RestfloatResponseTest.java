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

// Originally from UpdatedComponentTests/ResponseTypes/PrimitiveResponseTypes/float/REST/Rest_floatResponse.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.responsetypes.primitiveresponsetypes.floatz.rest;

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
 * Test that the Disco service allows to use float primitive type in the response (REST)
 */
public class RestfloatResponseTest {
    @Test
    public void doTest() throws Exception {
        // Get an HTTPCallBean
        DiscoManager discoManager1 = DiscoManager.getInstance();
        HttpCallBean HTTPCallBean = discoManager1.getNewHttpCallBean("87.248.113.14");
        DiscoManager DiscoManager = discoManager1;
        // Get LogManager JMX Attribute
        // Set Disco Fault Controller attributes
        DiscoManager.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");
        // Set operation  name 
        HTTPCallBean.setOperationName("floatSimpleTypeEcho", "floatEcho");
        // Set service name to call
        HTTPCallBean.setServiceName("baseline", "discoBaseline");
        // Set service version to call
        HTTPCallBean.setVersion("v2");
        // Set Query parameter (?msg=true)
        Map map2 = new HashMap();
        map2.put("msg","0.6");
        HTTPCallBean.setQueryParams(map2);
        // Get current time

        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        // Make Rest calls (makes 4 calls with different content/accept combinations of XML and JSON)
        DiscoManager.makeRestDiscoHTTPCalls(HTTPCallBean);
        // Create a REST response structure as a Document object
        XMLHelpers xMLHelpers4 = new XMLHelpers();
        Document responseDocument = xMLHelpers4.getXMLObjectFromString("<Float>0.6</Float>");
        // Convert the response document into Rest (XML and JSON) representations
        Map<DiscoMessageProtocolRequestTypeEnum, Object> convertedResponses = DiscoManager.convertResponseToRestTypes(responseDocument, HTTPCallBean);
        // Get the 4 results from the Rest calls and compare to the expected XML and JSON responses
        HttpResponseBean response5 = HTTPCallBean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLXML);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTXML), response5.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response5.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response5.getHttpStatusText());
        
        HttpResponseBean response6 = HTTPCallBean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTJSON), response6.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response6.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response6.getHttpStatusText());
        
        HttpResponseBean response7 = HTTPCallBean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLJSON);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTJSON), response7.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response7.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response7.getHttpStatusText());
        
        HttpResponseBean response8 = HTTPCallBean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(convertedResponses.get(DiscoMessageProtocolRequestTypeEnum.RESTXML), response8.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response8.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response8.getHttpStatusText());
        // Check Service log entries after the time recorded earlier in the test
        // Check Request log entries after the time recorded earlier in the test
        DiscoManager.verifyRequestLogEntriesAfterDate(timeStamp, new RequestLogRequirement("2.8", "floatSimpleTypeEcho"),new RequestLogRequirement("2.8", "floatSimpleTypeEcho"),new RequestLogRequirement("2.8", "floatSimpleTypeEcho"),new RequestLogRequirement("2.8", "floatSimpleTypeEcho") );
    }

}
