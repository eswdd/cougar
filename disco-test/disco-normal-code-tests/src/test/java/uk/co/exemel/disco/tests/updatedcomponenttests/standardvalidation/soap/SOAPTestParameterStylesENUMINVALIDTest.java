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

// Originally from UpdatedComponentTests/StandardValidation/SOAP/SOAP_TestParameterStyles_ENUM_INVALID.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardvalidation.soap;

import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.helpers.DiscoHelpers;
import uk.co.exemel.testing.utils.disco.manager.AccessLogRequirement;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;

/**
 * Ensure that when a SOAP request is received with an invalid value set in an ENUM field, Disco returns the correct fault
 * "
 */
public class SOAPTestParameterStylesENUMINVALIDTest {
    @Test(dataProvider = "SchemaValidationEnabled")
    public void doTest(boolean schemaValidationEnabled) throws Exception {
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
            // Create the SOAP request as an XML Document (with invalid ENUM parameter)
            XMLHelpers xMLHelpers2 = new XMLHelpers();
            Document createAsDocument2 = xMLHelpers2.getXMLObjectFromString("<TestParameterStylesQARequest><HeaderParam>FooManchu</HeaderParam><queryParam>qp1</queryParam><dateQueryParam>2009-06-01T13:50:00.0Z</dateQueryParam></TestParameterStylesQARequest>");
            // Set up the Http Call Bean to make the request
            DiscoManager discoManager3 = DiscoManager.getInstance();
            HttpCallBean getNewHttpCallBean3 = discoManager3.getNewHttpCallBean();
            discoManager3 = discoManager3;

            discoManager3.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");

            getNewHttpCallBean3.setServiceName("Baseline");

            getNewHttpCallBean3.setVersion("v2");
            // Set the created SOAP request as the PostObject
            getNewHttpCallBean3.setPostObjectForRequestType(createAsDocument2, "SOAP");
            // Get current time for getting log entries later

            Timestamp getTimeAsTimeStamp9 = new Timestamp(System.currentTimeMillis());
            // Make the SOAP call to the operation
            discoManager3.makeSoapDiscoHTTPCalls(getNewHttpCallBean3);
            // Create the expected response object as an XML document
            XMLHelpers xMLHelpers5 = new XMLHelpers();
            Document createAsDocument11 = xMLHelpers5.getXMLObjectFromString("<soapenv:Fault><faultcode>soapenv:Client</faultcode><faultstring>DSC-0044</faultstring><detail/></soapenv:Fault>");

            // Check the response is as expected
            HttpResponseBean response6 = getNewHttpCallBean3.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.SOAP);
            AssertionUtils.multiAssertEquals(createAsDocument11, response6.getResponseObject());
            // Check the log entries are as expected

            DiscoHelpers discoHelpers8 = new DiscoHelpers();
            String JavaVersion = discoHelpers8.getJavaVersion();

            DiscoManager discoManager9 = DiscoManager.getInstance();
            discoManager9.verifyAccessLogEntriesAfterDate(getTimeAsTimeStamp9, new AccessLogRequirement("87.248.113.14", "/BaselineService/v2", "BadRequest"));
        } finally {
            helpers.setSOAPSchemaValidationEnabled(true);
        }
    }

    @DataProvider(name = "SchemaValidationEnabled")
    public Object[][] versions() {
        return new Object[][]{
                {true}
                , {false}
        };
    }

}
