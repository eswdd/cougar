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

// Originally from UpdatedComponentTests/HealthCheck/Rest/Rest_HealthCheck_Detailed_ComponentStatusDetails_WithWarn.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.healthcheck.rest;

import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageContentTypeEnum;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolRequestTypeEnum;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;
import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

/**
 * Ensure that when a Disco container is running multiple services, the heathcheck detailed operation returns correct Status Details for each component that has a status set. (OK, WARN)
 */
public class RestHealthCheckDetailedComponentStatusDetailsWithWarnTest {

    @Test
    public void v3() throws Exception {
        // Set up the Http Call Bean to make the baseline service request
        DiscoManager discoManager1 = DiscoManager.getInstance();
        HttpCallBean getNewHttpCallBean1 = discoManager1.getNewHttpCallBean();
        try {

            getNewHttpCallBean1.setOperationName("setHealthStatusInfo");

            getNewHttpCallBean1.setServiceName("baseline", "discoBaseline");

            getNewHttpCallBean1.setVersion("v2");
            // Set the component statuses to be set (WARN most severe status)
            getNewHttpCallBean1.setRestPostQueryObjects(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream("<message><initialiseHealthStatusObject>true</initialiseHealthStatusObject><serviceStatusDetail>OK</serviceStatusDetail><DBConnectionStatusDetail>WARN</DBConnectionStatusDetail><cacheAccessStatusDetail>OK</cacheAccessStatusDetail></message>".getBytes())));
            // Make the REST call to the set the health statuses
            discoManager1.makeRestDiscoHTTPCall(getNewHttpCallBean1, DiscoMessageProtocolRequestTypeEnum.RESTXML, DiscoMessageContentTypeEnum.XML);
            // Set up the Http Call Bean to make the healthcheck service request
            HttpCallBean getNewHttpCallBean7 = discoManager1.getNewHttpCallBean();

            getNewHttpCallBean7.setOperationName("getDetailedHealthStatus", "detailed");

            getNewHttpCallBean7.setServiceName("healthcheck");

            getNewHttpCallBean7.setVersion("v3");

            getNewHttpCallBean7.setNameSpaceServiceName("Health");
            // Make the REST call to the get the health statuses from the health service
            discoManager1.makeRestDiscoHTTPCall(getNewHttpCallBean7, DiscoMessageProtocolRequestTypeEnum.RESTXML, DiscoMessageContentTypeEnum.XML);
            // Get the xml response and grab all the HealthDetail entries
            HttpResponseBean response3 = getNewHttpCallBean7.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTXMLXML);
            Document xmlResponse = (Document) response3.getResponseObject();
            AssertionUtils.multiAssertEquals((int) 200, response3.getHttpStatusCode());
            AssertionUtils.multiAssertEquals("OK", response3.getHttpStatusText());

            NodeList nodeList = xmlResponse.getElementsByTagName("HealthDetailResponse");
            Node healthDetailResponseNode = nodeList.item(0);
            // Get the HealthDetail entry for the Baseline service version 2.8
            XMLHelpers xmlHelpers = new XMLHelpers();
            // Get the subComponentList from the HealthDetail entry for Baseline
            Node baselineSubComponentList = xmlHelpers.getSpecifiedChildNode(healthDetailResponseNode, "subComponentList");
            // Get the Cache entry from the subComponentList and check the value of the status field is OK
            Node baselineCacheComponent = xmlHelpers.getNodeContainingSpecifiedChildNodeFromParent(baselineSubComponentList, "name", "Cache1");
            String status = xmlHelpers.getTextContentFromChildNode(baselineCacheComponent, "status");
            AssertionUtils.multiAssertEquals("OK", status);
            // Get the Service entry from the subComponentList and check the value of the status field is OK
            Node baselineServiceComponent = xmlHelpers.getNodeContainingSpecifiedChildNodeFromParent(baselineSubComponentList, "name", "Service1");
            status = xmlHelpers.getTextContentFromChildNode(baselineServiceComponent, "status");
            AssertionUtils.multiAssertEquals("OK", status);
            // Get the DB entry from the subComponentList and check the value of the status field is WARN
            Node baselineDBComponent = xmlHelpers.getNodeContainingSpecifiedChildNodeFromParent(baselineSubComponentList, "name", "DB1");
            status = xmlHelpers.getTextContentFromChildNode(baselineDBComponent, "status");
            AssertionUtils.multiAssertEquals("WARN", status);
            // Get the health entry from the xml response and check the value is WARN
            AssertionUtils.multiAssertEquals("WARN", xmlHelpers.getTextContentFromChildNode(healthDetailResponseNode, "health"));
        }
        finally {
            getNewHttpCallBean1 = discoManager1.getNewHttpCallBean();
            getNewHttpCallBean1.setOperationName("setHealthStatusInfo");

            getNewHttpCallBean1.setServiceName("baseline", "discoBaseline");

            getNewHttpCallBean1.setVersion("v2");
            // Set the component statuses to be set (Including a FAIL status)
            getNewHttpCallBean1.setRestPostQueryObjects(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream("<message><initialiseHealthStatusObject>true</initialiseHealthStatusObject><serviceStatusDetail>OK</serviceStatusDetail><DBConnectionStatusDetail>WARN</DBConnectionStatusDetail><cacheAccessStatusDetail>OK</cacheAccessStatusDetail></message>".getBytes())));
            // Make the REST call to the set the health statuses
            discoManager1.makeRestDiscoHTTPCall(getNewHttpCallBean1, DiscoMessageProtocolRequestTypeEnum.RESTXML, DiscoMessageContentTypeEnum.XML);
        }
    }

}
