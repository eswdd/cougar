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

// Originally from UpdatedComponentTests/StandardTesting/SOAP/SOAP_TestLargeGet_LISTS_2.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.standardtesting.soap;

import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;
import uk.co.exemel.testing.utils.disco.manager.RequestLogRequirement;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;

/**
 * Test to call the testLargeGetRequest operation to check that disco can return a list containing the given number of items (EVEN)
 */
public class SOAPTestLargeGetLISTS2Test {
    @Test
    public void doTest() throws Exception {
        // Create the SOAP request as an XML Document (giving the number of items expected in the returned list)
        XMLHelpers xMLHelpers1 = new XMLHelpers();
        Document createAsDocument1 = xMLHelpers1.getXMLObjectFromString("<TestLargeGetRequest><size>10</size></TestLargeGetRequest>");
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
        // Create the expected response object as an XML document (A list with the request number of items in it)
        XMLHelpers xMLHelpers4 = new XMLHelpers();
        Document createAsDocument10 = xMLHelpers4.getXMLObjectFromString("<response><size>10</size><objects><ComplexObject><name>name 0</name><value1>0</value1><value2>1</value2></ComplexObject><ComplexObject><name>name 1</name><value1>1</value1><value2>2</value2></ComplexObject><ComplexObject><name>name 2</name><value1>2</value1><value2>3</value2></ComplexObject><ComplexObject><name>name 3</name><value1>3</value1><value2>4</value2></ComplexObject><ComplexObject><name>name 4</name><value1>4</value1><value2>5</value2></ComplexObject><ComplexObject><name>name 5</name><value1>5</value1><value2>6</value2></ComplexObject><ComplexObject><name>name 6</name><value1>6</value1><value2>7</value2></ComplexObject><ComplexObject><name>name 7</name><value1>7</value1><value2>8</value2></ComplexObject><ComplexObject><name>name 8</name><value1>8</value1><value2>9</value2></ComplexObject><ComplexObject><name>name 9</name><value1>9</value1><value2>10</value2></ComplexObject></objects><oddOrEven>EVEN</oddOrEven></response>");
        // Check the response is as expected
        HttpResponseBean response5 = getNewHttpCallBean2.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.SOAP);
        AssertionUtils.multiAssertEquals(createAsDocument10, response5.getResponseObject());

        // generalHelpers.pauseTest(500L);
        // Check the log entries are as expected

        discoManager2.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp8, new RequestLogRequirement("2.8", "testLargeGet") );
    }

}
