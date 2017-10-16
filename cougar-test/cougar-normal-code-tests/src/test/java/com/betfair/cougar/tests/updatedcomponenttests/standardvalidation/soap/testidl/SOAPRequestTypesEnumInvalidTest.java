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
package com.betfair.cougar.tests.updatedcomponenttests.standardvalidation.soap.testidl;

import com.betfair.testing.utils.cougar.assertions.AssertionUtils;
import com.betfair.testing.utils.cougar.beans.HttpCallBean;
import com.betfair.testing.utils.cougar.beans.HttpResponseBean;
import com.betfair.testing.utils.cougar.helpers.CougarHelpers;
import com.betfair.testing.utils.cougar.manager.AccessLogRequirement;
import com.betfair.testing.utils.cougar.manager.CougarManager;
import com.betfair.testing.utils.cougar.misc.XMLHelpers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Ensure that when a SOAP request is received with a null Byte parameter Cougar returns the correct fault
 */
public class SOAPRequestTypesEnumInvalidTest {
    @Test(dataProvider = "ValidationStyleAndFailureLocation")
    public void doTest(boolean schemaValidationEnabled, String location) throws Exception {
        CougarHelpers helpers = new CougarHelpers();
        try {
            CougarManager cougarManager = CougarManager.getInstance();
            helpers.setSOAPSchemaValidationEnabled(schemaValidationEnabled);
            // Create the HttpCallBean
            CougarManager cougarManager1 = CougarManager.getInstance();
            HttpCallBean httpCallBeanBaseline = cougarManager1.getNewHttpCallBean();
            CougarManager cougarManagerBaseline = cougarManager1;
            // Get the cougar logging attribute for getting log entries later
            // Point the created HttpCallBean at the correct service
            httpCallBeanBaseline.setServiceName("baseline", "cougarBaseline");

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
            CougarManager cougarManager3 = CougarManager.getInstance();
            HttpCallBean hbean = cougarManager3.getNewHttpCallBean("87.248.113.14");
            CougarManager hinstance = cougarManager3;

            hinstance.setCougarFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");

            hbean.setServiceName("Baseline");

            hbean.setVersion("v2");
            // Set the created SOAP request as the PostObject
            hbean.setPostObjectForRequestType(createAsDocument2, "SOAP");
            // Get current time for getting log entries later

            Timestamp getTimeAsTimeStamp9 = new Timestamp(System.currentTimeMillis());
            // Make the SOAP call to the operation
            hinstance.makeSoapCougarHTTPCalls(hbean);
            // Create the expected response object as an XML document (fault)
            XMLHelpers xMLHelpers5 = new XMLHelpers();
            Document createAsDocument11 = xMLHelpers5.getXMLObjectFromString("<soapenv:Fault><faultcode>soapenv:Client</faultcode><faultstring>DSC-0044</faultstring><detail/></soapenv:Fault>");
            // Check the response is as expected
            HttpResponseBean response6 = hbean.getResponseObjectsByEnum(com.betfair.testing.utils.cougar.enums.CougarMessageProtocolResponseTypeEnum.SOAP);
            AssertionUtils.multiAssertEquals(createAsDocument11, response6.getResponseObject());

            // generalHelpers.pauseTest(500L);
            // Check the log entries are as expected

            CougarHelpers cougarHelpers9 = new CougarHelpers();
            String JavaVersion = cougarHelpers9.getJavaVersion();

            CougarManager cougarManager10 = CougarManager.getInstance();
            cougarManager10.verifyAccessLogEntriesAfterDate(getTimeAsTimeStamp9, new AccessLogRequirement("87.248.113.14", "/BaselineService/v2", "BadRequest"));
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
