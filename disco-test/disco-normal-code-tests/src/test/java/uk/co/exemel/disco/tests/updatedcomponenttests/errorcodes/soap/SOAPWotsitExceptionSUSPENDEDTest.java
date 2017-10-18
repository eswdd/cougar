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

// Originally from UpdatedComponentTests/ErrorCodes/SOAP/SOAP_WotsitException_SUSPENDED.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.errorcodes.soap;

import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;

/**
 * Ensure that the Container can return a SOAPMessage appropriate for a fault when an operation has failed. In this case that a SUSPENDED exception has been thrown.
 */
public class SOAPWotsitExceptionSUSPENDEDTest {
    @Test
    public void doTest() throws Exception {
        // Create the request object as an XML document specifying the exception that should be thrown (SUSPENDED)
        XMLHelpers xMLHelpers1 = new XMLHelpers();
        Document createAsDocument1 = xMLHelpers1.getXMLObjectFromString("<TestExceptionQARequest><message>SUSPENDED</message></TestExceptionQARequest>");
        // Set up the Http Call Bean to make the request
        DiscoManager discoManager2 = DiscoManager.getInstance();
        HttpCallBean getNewHttpCallBean2 = discoManager2.getNewHttpCallBean("87.248.113.14");
        discoManager2 = discoManager2;
        // Get the disco log attribute for getting log entries later
        
        discoManager2.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");
        
        getNewHttpCallBean2.setServiceName("Baseline");
        
        getNewHttpCallBean2.setVersion("v2");
        
        getNewHttpCallBean2.setPostObjectForRequestType(createAsDocument1, "SOAP");
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp8 = new Timestamp(System.currentTimeMillis());
        // Make the SOAP call to the operation
        discoManager2.makeSoapDiscoHTTPCalls(getNewHttpCallBean2);
        // Create the expected response as an XML document (the required exception)
        XMLHelpers xMLHelpers4 = new XMLHelpers();
        Document createAsDocument10 = xMLHelpers4.getXMLObjectFromString("<soapenv:Fault><faultcode>soapenv:Client</faultcode><faultstring>WEX-0002</faultstring><detail><bas:WotsitException><bas:errorCode>SUSPENDED</bas:errorCode><bas:type>CHEESY</bas:type><bas:clientMessage>SUSPENDED</bas:clientMessage></bas:WotsitException></detail></soapenv:Fault>");
        // Check the response is as expected (correct error returned)
        HttpResponseBean response5 = getNewHttpCallBean2.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.SOAP);
        AssertionUtils.multiAssertEquals(createAsDocument10, response5.getResponseObject());
        // Check the log entries are as expected
    }

}
