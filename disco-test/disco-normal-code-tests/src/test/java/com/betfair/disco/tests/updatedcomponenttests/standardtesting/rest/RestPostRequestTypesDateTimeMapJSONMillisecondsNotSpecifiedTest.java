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

// Originally from UpdatedComponentTests/StandardTesting/REST/Rest_Post_RequestTypes_DateTimeMap_JSON_MillisecondsNotSpecified.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardtesting.rest;

import com.betfair.testing.utils.disco.misc.TimingHelpers;
import com.betfair.testing.utils.disco.misc.XMLHelpers;
import com.betfair.testing.utils.JSONHelpers;
import com.betfair.testing.utils.disco.assertions.AssertionUtils;
import com.betfair.testing.utils.disco.beans.HttpCallBean;
import com.betfair.testing.utils.disco.beans.HttpResponseBean;
import com.betfair.testing.utils.disco.manager.DiscoManager;
import com.betfair.testing.utils.disco.manager.RequestLogRequirement;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure that Disco can handle the dateTimeMap data type in the post body of a JSON request containg a date with no milliseconds specified
 */
public class RestPostRequestTypesDateTimeMapJSONMillisecondsNotSpecifiedTest {
    @Test
    public void doTest() throws Exception {
        // Set up the Http Call Bean to make the request
        DiscoManager discoManager1 = DiscoManager.getInstance();
        HttpCallBean hbean = discoManager1.getNewHttpCallBean("87.248.113.14");
        DiscoManager hinstance = discoManager1;
        
        hbean.setOperationName("dateTimeMapOperation");
        
        hbean.setServiceName("baseline", "discoBaseline");
        
        hbean.setVersion("v2");
        // Create a date time object expected to be in the response object

        String date1 = TimingHelpers.convertUTCDateTimeToDiscoFormat((int) 2009, (int) 6, (int) 1, (int) 13, (int) 50, (int) 0, (int) 0);
        // Create a date time object expected to be in the response object

        String date2 = TimingHelpers.convertUTCDateTimeToDiscoFormat((int) 2009, (int) 6, (int) 1, (int) 14, (int) 50, (int) 0, (int) 0);
        // Set the post body to contain a date time map object
        Map map4 = new HashMap();
        map4.put("RESTJSON","{\"message\":{\"dateTimeMap\":{   \n\"date1\":\"2009-06-01T13:50:00Z\",                   \"date2\":\"2009-06-01T14:50:00Z\"} \n}}");
        hbean.setPostQueryObjects(map4);
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp11 = new Timestamp(System.currentTimeMillis());
        // Make JSON call to the operation requesting an XML response
        hinstance.makeRestDiscoHTTPCall(hbean, com.betfair.testing.utils.disco.enums.DiscoMessageProtocolRequestTypeEnum.RESTJSON, com.betfair.testing.utils.disco.enums.DiscoMessageContentTypeEnum.XML);
        // Make JSON call to the operation requesting a JSON response
        hinstance.makeRestDiscoHTTPCall(hbean, com.betfair.testing.utils.disco.enums.DiscoMessageProtocolRequestTypeEnum.RESTJSON, com.betfair.testing.utils.disco.enums.DiscoMessageContentTypeEnum.JSON);
        // Create the expected response as an XML document (using the date object created earlier)
        XMLHelpers xMLHelpers6 = new XMLHelpers();
        Document expectedResponseXML = xMLHelpers6.createAsDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(("<DateTimeMapOperationResponse><DateTimeMapOperationResponseObject><responseMap><entry key=\"date2\"><Date>"+date2+"</Date></entry><entry key=\"date1\"><Date>"+date1+"</Date></entry></responseMap></DateTimeMapOperationResponseObject></DateTimeMapOperationResponse>").getBytes())));
        // Create the expected response as a JSON object (using the date object created earlier)
        JSONHelpers jSONHelpers7 = new JSONHelpers();
        JSONObject expectedResponseJSON = jSONHelpers7.createAsJSONObject(new JSONObject("{responseMap:{\"date2\":\""+date2+"\",\"date1\":\""+date1+"\"}}"));
        // Check the 2 responses are as expected
        HttpResponseBean response8 = hbean.getResponseObjectsByEnum(com.betfair.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(expectedResponseXML, response8.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response8.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response8.getHttpStatusText());
        
        HttpResponseBean response9 = hbean.getResponseObjectsByEnum(com.betfair.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(expectedResponseJSON, response9.getResponseObject());
        AssertionUtils.multiAssertEquals((int) 200, response9.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response9.getHttpStatusText());
        
        // generalHelpers.pauseTest(500L);
        // Check the log entries are as expected
        
        hinstance.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp11, new RequestLogRequirement("2.8", "dateTimeMapOperation"),new RequestLogRequirement("2.8", "dateTimeMapOperation") );
    }

}