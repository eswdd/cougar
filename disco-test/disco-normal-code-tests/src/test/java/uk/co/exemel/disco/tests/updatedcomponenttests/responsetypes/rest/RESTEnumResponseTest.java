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

// Originally from UpdatedComponentTests/ResponseTypes/REST/REST_EnumResponse.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.responsetypes.rest;

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
import java.util.Map;

/**
 * Test that the Disco service allows to use enumerations in the response (REST)
 */
public class RESTEnumResponseTest {
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
        HTTPCallBean.setOperationName("callWithEnumResponse", "callWithEnumResponse");
        // Set service name to call
        HTTPCallBean.setServiceName("baseline", "discoBaseline");
        // Set service version to call
        HTTPCallBean.setVersion("v2");
        // Get current time

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        // Make Disco REST call
        DiscoManager.makeRestDiscoHTTPCalls(HTTPCallBean);
        // Create a REST response structure as a Document object
        XMLHelpers xMLHelpers3 = new XMLHelpers();
        Document responseDocument = xMLHelpers3.getXMLObjectFromString("<SimpleValidValue>WEASEL</SimpleValidValue>");
        // Convert the Document to REST
        Map<DiscoMessageProtocolRequestTypeEnum, Object> response5 = DiscoManager.convertResponseToRestTypes(responseDocument, HTTPCallBean);
        Object responseRESTXML = response5.get(DiscoMessageProtocolRequestTypeEnum.RESTXML);
        Object responseRESTJSON = response5.get(DiscoMessageProtocolRequestTypeEnum.RESTJSON);
        // Get the actual REST response and compare it to the expected response
        HttpResponseBean response6 = HTTPCallBean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLXML);
        AssertionUtils.multiAssertEquals(responseRESTXML, response6.getResponseObject());
        // Get the actual REST response and compare it to the expected response
        HttpResponseBean response7 = HTTPCallBean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(responseRESTJSON, response7.getResponseObject());
        // Get the actual REST response and compare it to the expected response
        HttpResponseBean response8 = HTTPCallBean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLJSON);
        AssertionUtils.multiAssertEquals(responseRESTJSON, response8.getResponseObject());
        // Get the actual REST response and compare it to the expected response
        HttpResponseBean response9 = HTTPCallBean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(responseRESTXML, response9.getResponseObject());
        // Pause test
        // generalHelpers.pauseTest(500L);
        // Get Service log entries after the time recorded earlier in the test
        // Get request log entries after the time recorded earlier in the test
        DiscoManager.verifyRequestLogEntriesAfterDate(timestamp, new RequestLogRequirement("2.8", "callWithEnumResponse"),new RequestLogRequirement("2.8", "callWithEnumResponse"),new RequestLogRequirement("2.8", "callWithEnumResponse"),new RequestLogRequirement("2.8", "callWithEnumResponse") );
    }

}
