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

// Originally from UpdatedComponentTests/StandardTesting/REST/Rest_Post_RequestTypes_DateTimeSet_24Hour.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardtesting.rest;

import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import uk.co.exemel.testing.utils.JSONHelpers;
import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.manager.AccessLogRequirement;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.sql.Timestamp;

/**
 * Ensure that Disco can handle the dateTimeSet data type in the post body containing dates with the time set to 24:00:00
 */
public class RestPostRequestTypesDateTimeSet24HourTest {
    @Test
    public void doTest() throws Exception {
        // Set up the Http Call Bean to make the request
        DiscoManager discoManager1 = DiscoManager.getInstance();
        HttpCallBean hbean = discoManager1.getNewHttpCallBean("87.248.113.14");
        DiscoManager hinstance = discoManager1;
        discoManager1.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults","false");
        try {

            hbean.setOperationName("dateTimeSetOperation");

            hbean.setServiceName("baseline", "discoBaseline");

            hbean.setVersion("v2");
            // Set the post body to contain a date time set object
            hbean.setRestPostQueryObjects(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream("<message><dateTimeSet><Date>2009-06-01T24:00:00.0Z</Date><Date>2009-07-01T24:00:00.0Z</Date></dateTimeSet></message>".getBytes())));
            // Get current time for getting log entries later

            Timestamp getTimeAsTimeStamp11 = new Timestamp(System.currentTimeMillis());
            // Make the 4 REST calls to the operation
            hinstance.makeRestDiscoHTTPCalls(hbean);
            // Create the expected response as an XML document (using the date object created earlier)
            XMLHelpers xMLHelpers5 = new XMLHelpers();
            Document responseXMLForXMLInput = xMLHelpers5.createAsDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(("<fault><faultcode>Client</faultcode><faultstring>DSC-0044</faultstring><detail/></fault>").getBytes())));
            Document responseXMLForJSONInput = xMLHelpers5.createAsDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(("<fault><faultcode>Client</faultcode><faultstring>DSC-0044</faultstring><detail/></fault>").getBytes())));
            // Create the expected response as a JSON object (using the date object created earlier)
            JSONHelpers jSONHelpers6 = new JSONHelpers();
            JSONObject responseJSONForJSONInput = jSONHelpers6.createAsJSONObject(new JSONObject("{\"detail\":{},\"faultcode\":\"Client\",\"faultstring\":\"DSC-0044\"}"));
            JSONObject responseJSONForXMLInput = jSONHelpers6.createAsJSONObject(new JSONObject("{\"detail\":{},\"faultcode\":\"Client\",\"faultstring\":\"DSC-0044\"}"));
            // Check the 4 responses are as expected
            HttpResponseBean response7 = hbean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLXML);
            AssertionUtils.multiAssertEquals(responseXMLForXMLInput, response7.getResponseObject());
            AssertionUtils.multiAssertEquals((int) 400, response7.getHttpStatusCode());
            AssertionUtils.multiAssertEquals("Bad Request", response7.getHttpStatusText());

            HttpResponseBean response8 = hbean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
            AssertionUtils.multiAssertEquals(responseJSONForJSONInput, response8.getResponseObject());
            AssertionUtils.multiAssertEquals((int) 400, response8.getHttpStatusCode());
            AssertionUtils.multiAssertEquals("Bad Request", response8.getHttpStatusText());

            HttpResponseBean response9 = hbean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLJSON);
            AssertionUtils.multiAssertEquals(responseJSONForXMLInput, response9.getResponseObject());
            AssertionUtils.multiAssertEquals((int) 400, response9.getHttpStatusCode());
            AssertionUtils.multiAssertEquals("Bad Request", response9.getHttpStatusText());

            HttpResponseBean response10 = hbean.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONXML);
            AssertionUtils.multiAssertEquals(responseXMLForJSONInput, response10.getResponseObject());
            AssertionUtils.multiAssertEquals((int) 400, response10.getHttpStatusCode());
            AssertionUtils.multiAssertEquals("Bad Request", response10.getHttpStatusText());

            // Check the log entries are as expected


        discoManager1.verifyAccessLogEntriesAfterDate(getTimeAsTimeStamp11, new AccessLogRequirement("87.248.113.14", "/discoBaseline/v2/dateTimeSetOperation", "BadRequest"),new AccessLogRequirement("87.248.113.14", "/discoBaseline/v2/dateTimeSetOperation", "BadRequest"),new AccessLogRequirement("87.248.113.14", "/discoBaseline/v2/dateTimeSetOperation", "BadRequest"),new AccessLogRequirement("87.248.113.14", "/discoBaseline/v2/dateTimeSetOperation", "BadRequest") );
        }
        finally {
            discoManager1.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults","true");
        }
    }

}
