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

// Originally from UpdatedComponentTests/StandardValidation/REST/Rest_IDL_PostBodyElement_Integer_IncorrectDataType.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardvalidation.rest;

import com.betfair.testing.utils.disco.misc.XMLHelpers;
import com.betfair.testing.utils.JSONHelpers;
import com.betfair.testing.utils.disco.assertions.AssertionUtils;
import com.betfair.testing.utils.disco.beans.HttpCallBean;
import com.betfair.testing.utils.disco.beans.HttpResponseBean;
import com.betfair.testing.utils.disco.manager.AccessLogRequirement;
import com.betfair.testing.utils.disco.manager.DiscoManager;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.sql.Timestamp;

/**
 * Ensure that Disco returns the correct fault, when a REST request contains a non Integer as an Integer body parameter
 */
public class RestIDLPostBodyElementIntegerIncorrectDataTypeTest {
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
        HttpCallBean getNewHttpCallBean2 = discoManager2.getNewHttpCallBean("87.248.113.14");
        discoManager2 = discoManager2;

        discoManager2.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");

        getNewHttpCallBean2.setOperationName("testComplexMutator", "complex");

        getNewHttpCallBean2.setServiceName("baseline", "discoBaseline");

        getNewHttpCallBean2.setVersion("v2");
        // Set the body parameter, including an Integer field set to a non Integer (value1 set to a String instead)
        getNewHttpCallBean2.setRestPostQueryObjects(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream("<message><name>sum</name><value1>aString</value1><value2>75</value2></message>".getBytes())));
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp9 = new Timestamp(System.currentTimeMillis());
        // Make the 4 REST calls to the operation
        discoManager2.makeRestDiscoHTTPCalls(getNewHttpCallBean2);
        // Create the expected response to XML requests as an XML document (XML Fault)
        XMLHelpers xMLHelpers4 = new XMLHelpers();
        Document createAsDocument11 = xMLHelpers4.getXMLObjectFromString("<fault><faultcode>Client</faultcode><faultstring>DSC-0044</faultstring><detail/></fault>");
        // Create the expected response to JSON requests as an XML document (JSON Fault)
        XMLHelpers xMLHelpers5 = new XMLHelpers();
        Document createAsDocument12 = xMLHelpers5.getXMLObjectFromString("<fault><faultcode>Client</faultcode><faultstring>DSC-0044</faultstring><detail/></fault>");
        // Convert expected response to XML requests to JSON object
        JSONHelpers jSONHelpers6 = new JSONHelpers();
        JSONObject convertXMLDocumentToJSONObjectRemoveRootElement13 = jSONHelpers6.convertXMLDocumentToJSONObjectRemoveRootElement(createAsDocument11);
        // Convert expected response to JSON requests to JSON object
        JSONHelpers jSONHelpers7 = new JSONHelpers();
        JSONObject convertXMLDocumentToJSONObjectRemoveRootElement14 = jSONHelpers7.convertXMLDocumentToJSONObjectRemoveRootElement(createAsDocument12);
        // Check the 4 responses are as expected (Bad Request)
        HttpResponseBean response8 = getNewHttpCallBean2.getResponseObjectsByEnum(com.betfair.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum.RESTXMLXML);
        AssertionUtils.multiAssertEquals(createAsDocument11, response8.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 400, response8.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Bad Request", response8.getHttpStatusText());

        HttpResponseBean response9 = getNewHttpCallBean2.getResponseObjectsByEnum(com.betfair.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(convertXMLDocumentToJSONObjectRemoveRootElement14, response9.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 400, response9.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Bad Request", response9.getHttpStatusText());

        HttpResponseBean response10 = getNewHttpCallBean2.getResponseObjectsByEnum(com.betfair.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum.RESTXMLJSON);
        AssertionUtils.multiAssertEquals(convertXMLDocumentToJSONObjectRemoveRootElement13, response10.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 400, response10.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Bad Request", response10.getHttpStatusText());

        HttpResponseBean response11 = getNewHttpCallBean2.getResponseObjectsByEnum(com.betfair.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(createAsDocument12, response11.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 400, response11.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("Bad Request", response11.getHttpStatusText());
        // Check the log entries are as expected

        DiscoManager discoManager13 = DiscoManager.getInstance();
        discoManager13.verifyAccessLogEntriesAfterDate(getTimeAsTimeStamp9, new AccessLogRequirement("87.248.113.14", "/discoBaseline/v2/complex", "BadRequest"),new AccessLogRequirement("87.248.113.14", "/discoBaseline/v2/complex", "BadRequest"),new AccessLogRequirement("87.248.113.14", "/discoBaseline/v2/complex", "BadRequest"),new AccessLogRequirement("87.248.113.14", "/discoBaseline/v2/complex", "BadRequest") );
    }

}