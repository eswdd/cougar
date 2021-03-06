/*
 * Copyright 2013, The Sporting Exchange Limited
 * Copyright 2014, Simon Matić Langford
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

// Originally from UpdatedComponentTests/StandardValidation/SOAP/Test-IDL/SOAP_RequestTypes_Byte_null.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardvalidation.soap.testidl;

import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.helpers.DiscoHelpers;
import uk.co.exemel.testing.utils.disco.manager.AccessLogRequirement;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;
import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;

/**
 * Ensure that when a SOAP request is received with a null Byte parameter Disco returns the correct fault
 */
public class SOAPRequestTypesEnumInvalidTest {
    @Test(dataProvider = "ValidationStyleAndFailureLocation")
    public void doTest(boolean schemaValidationEnabled, String location) throws Exception {
        DiscoHelpers helpers = new DiscoHelpers();
        try {
            DiscoManager discoManager = DiscoManager.getInstance();
            helpers.setSOAPSchemaValidationEnabled(schemaValidationEnabled);
            // Create the HttpCallBean
            DiscoManager discoManager1 = DiscoManager.getInstance();
            HttpCallBean httpCallBeanBaseline = discoManager1.getNewHttpCallBean();
            DiscoManager discoManagerBaseline = discoManager1;
            // Get the disco logging attribute for getting log entries later
            // Point the created HttpCallBean at the correct service
            httpCallBeanBaseline.setServiceName("baseline", "discoBaseline");

            httpCallBeanBaseline.setVersion("v2");
            // Create the SOAP request as an XML Document (with a null byte parameter)
            XMLHelpers xMLHelpers2 = new XMLHelpers();
            Document createAsDocument2 = xMLHelpers2.getXMLObjectFromString("<EnumSimpleOperationRequest><message><bodyParameter>"
                    +(location.equals("body")?"wibble":"FOO")+
                    "</bodyParameter></message><headerParam>"
                    +(location.equals("header")?"wibble":"FOOBAR")+
                    "</headerParam><queryParam>"
                    +(location.equals("query")?"wibble":"BAR")+
                    "</queryParam></EnumSimpleOperationRequest>");
            // Set up the Http Call Bean to make the request
            DiscoManager discoManager3 = DiscoManager.getInstance();
            HttpCallBean hbean = discoManager3.getNewHttpCallBean("87.248.113.14");
            DiscoManager hinstance = discoManager3;

            hinstance.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");

            hbean.setServiceName("Baseline");

            hbean.setVersion("v2");
            // Set the created SOAP request as the PostObject
            hbean.setPostObjectForRequestType(createAsDocument2, "SOAP");
            // Get current time for getting log entries later

            Timestamp getTimeAsTimeStamp9 = new Timestamp(System.currentTimeMillis());
            // Make the SOAP call to the operation
            hinstance.makeSoapDiscoHTTPCalls(hbean);
            // Create the expected response object as an XML document (fault)
            XMLHelpers xMLHelpers5 = new XMLHelpers();
            Document createAsDocument11 = xMLHelpers5.getXMLObjectFromString("<soapenv:Fault><faultcode>soapenv:Client</faultcode><faultstring>DSC-0044</faultstring><detail/></soapenv:Fault>");
            // Check the response is as expected
            HttpResponseBean response6 = hbean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.SOAP);
            AssertionUtils.multiAssertEquals(createAsDocument11, response6.getResponseObject());

            // generalHelpers.pauseTest(500L);
            // Check the log entries are as expected

            DiscoHelpers discoHelpers9 = new DiscoHelpers();
            String JavaVersion = discoHelpers9.getJavaVersion();

            DiscoManager discoManager10 = DiscoManager.getInstance();
            discoManager10.verifyAccessLogEntriesAfterDate(getTimeAsTimeStamp9, new AccessLogRequirement("87.248.113.14", "/BaselineService/v2", "BadRequest"));
        } finally {
            helpers.setSOAPSchemaValidationEnabled(true);
        }
    }

    @DataProvider(name = "ValidationStyleAndFailureLocation")
    public Object[][] versions() {
        return new Object[][]{
                {true,"body"},
                {true,"header"},
                {true,"query"},
                {false,"body"},
                {false,"header"},
                {false,"query"}
        };
    }

}
