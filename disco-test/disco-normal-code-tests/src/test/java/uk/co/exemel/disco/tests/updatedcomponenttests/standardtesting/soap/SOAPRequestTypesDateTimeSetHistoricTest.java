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

// Originally from UpdatedComponentTests/StandardTesting/SOAP/SOAP_RequestTypes_DateTimeSet_Historic.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardtesting.soap;

import uk.co.exemel.testing.utils.disco.misc.TimingHelpers;
import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;
import uk.co.exemel.testing.utils.disco.manager.RequestLogRequirement;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.sql.Timestamp;

/**
 * Ensure that when a SOAP request is received, Disco can handle the dateTimeSet datatype containing a date set far in the past
 */
public class SOAPRequestTypesDateTimeSetHistoricTest {
    @Test
    public void doTest() throws Exception {
        // Create the SOAP request as an XML Document (with a dateTimeSet parameter containing a date set far in the past)
        XMLHelpers xMLHelpers1 = new XMLHelpers();
        Document createAsDocument1 = xMLHelpers1.getXMLObjectFromString("<DateTimeSetOperationRequest><message><dateTimeSet><Date>1899-06-01T13:50:00.0Z</Date><Date>1899-06-02T13:50:00.0Z</Date></dateTimeSet></message></DateTimeSetOperationRequest>");
        // Set up the Http Call Bean to make the request
        DiscoManager discoManager2 = DiscoManager.getInstance();
        HttpCallBean hbean = discoManager2.getNewHttpCallBean("87.248.113.14");
        DiscoManager hinstance = discoManager2;

        hbean.setServiceName("Baseline");

        hbean.setVersion("v2");
        // Create the date objects expected to be returned in the response

        String convertUTCDateTimeToDiscoFormat6 = TimingHelpers.convertUTCDateTimeToDiscoFormat((int) 1899, (int) 6, (int) 1, (int) 13, (int) 50, (int) 0, (int) 0);
        // Create the date objects expected to be returned in the response

        String convertUTCDateTimeToDiscoFormat7 = TimingHelpers.convertUTCDateTimeToDiscoFormat((int) 1899, (int) 6, (int) 2, (int) 13, (int) 50, (int) 0, (int) 0);
        // Set the created SOAP request as the PostObject
        hbean.setPostObjectForRequestType(createAsDocument1, "SOAP");
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp9 = new Timestamp(System.currentTimeMillis());
        // Make the SOAP call to the operation
        hinstance.makeSoapDiscoHTTPCalls(hbean);
        // Create the expected response object as an XML document (using the date objects created earlier)
        XMLHelpers xMLHelpers6 = new XMLHelpers();
        Document createAsDocument11 = xMLHelpers6.createAsDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(("<response><responseSet><Date>"+convertUTCDateTimeToDiscoFormat6+"</Date><Date>"+convertUTCDateTimeToDiscoFormat7+"</Date></responseSet></response>").getBytes())));

        // Check the response is as expected
        HttpResponseBean response7 = hbean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.SOAP);
        AssertionUtils.multiAssertEquals(createAsDocument11, response7.getResponseObject());

        // generalHelpers.pauseTest(3000L);
        // Check the log entries are as expected

        hinstance.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp9, new RequestLogRequirement("2.8", "dateTimeSetOperation") );
    }

}
