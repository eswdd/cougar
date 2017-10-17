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

// Originally from UpdatedComponentTests/StandardTesting/SOAP/SOAP_TestLargePost_ZEROONEMANY_capacity.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardtesting.soap;

import com.betfair.testing.utils.disco.misc.XMLHelpers;
import com.betfair.testing.utils.disco.assertions.AssertionUtils;
import com.betfair.testing.utils.disco.beans.HttpCallBean;
import com.betfair.testing.utils.disco.beans.HttpResponseBean;
import com.betfair.testing.utils.disco.manager.DiscoManager;
import com.betfair.testing.utils.disco.manager.RequestLogRequirement;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Test that when Disco is passed a Large Request it can correctly deserialise it and count how many items are in the list (incorrect number  of items given - capacity of  large request filled)
 */
public class SOAPTestLargePostZEROONEMANYcapacityTest {
    @Test
    public void doTest() throws Exception {
        // Create the SOAP request as an XML Document
        XMLHelpers xMLHelpers1 = new XMLHelpers();
        Document createAsDocument1 = xMLHelpers1.getXMLObjectFromString("<TestLargePostRequest><largeRequest><size>1</size><objects><ComplexObject><name>ssasdf</name><value1>1</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>2</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>3</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>4</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>5</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>6</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>7</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>8</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>9</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>10</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>11</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>12</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>13</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>14</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>15</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>16</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>17</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>18</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>19</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>20</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>21</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>22</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>23</value1><value2>42</value2></ComplexObject><ComplexObject><name>ssasdf</name><value1>24</value1><value2>42</value2></ComplexObject></objects><oddOrEven>ODD</oddOrEven></largeRequest></TestLargePostRequest>");
        // Set up the Http Call Bean to make the request
        DiscoManager discoManager2 = DiscoManager.getInstance();
        HttpCallBean getNewHttpCallBean2 = discoManager2.getNewHttpCallBean("87.248.113.14");
        discoManager2 = discoManager2;

        discoManager2.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");

        getNewHttpCallBean2.setServiceName("Baseline");

        getNewHttpCallBean2.setVersion("v2");
        // Set the created SOAP request as the PostObject
        getNewHttpCallBean2.setPostObjectForRequestType(createAsDocument1, "SOAP");
        // Get current time for getting log entries later

        Timestamp getTimeAsTimeStamp8 = new Timestamp(System.currentTimeMillis());
        // Make the SOAP call to the operation
        discoManager2.makeSoapDiscoHTTPCalls(getNewHttpCallBean2);
        // Create the expected response object as an XML document (A message stating if the number of items in the list was correct)
        XMLHelpers xMLHelpers4 = new XMLHelpers();
        Document createAsDocument10 = xMLHelpers4.getXMLObjectFromString("<response><message>There were 1 items specified in the list, 24 actually</message></response>");
        // Check the response is as expected
        HttpResponseBean response5 = getNewHttpCallBean2.getResponseObjectsByEnum(com.betfair.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum.SOAP);
        AssertionUtils.multiAssertEquals(createAsDocument10, response5.getResponseObject());

        // generalHelpers.pauseTest(500L);
        // Check the log entries are as expected

        discoManager2.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp8, new RequestLogRequirement("2.8", "testLargePost") );
    }

}
