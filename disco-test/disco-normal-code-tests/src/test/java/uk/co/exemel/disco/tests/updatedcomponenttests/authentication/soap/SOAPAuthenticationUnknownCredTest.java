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

// Originally from UpdatedComponentTests/Authentication/SOAP/SOAP_Authentication_UnknownCred.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.authentication.soap;

import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.manager.AccessLogRequirement;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;
import uk.co.exemel.testing.utils.disco.manager.RequestLogRequirement;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Verify that an unknown pareameter added to Credentials auth header is ignored
 */
public class SOAPAuthenticationUnknownCredTest {
    @Test
    public void doTest() throws Exception {

        XMLHelpers xMLHelpers1 = new XMLHelpers();
        Document createAsDocument1 = xMLHelpers1.getXMLObjectFromString("<TestIdentityChainRequest/>");

        DiscoManager discoManager2 = DiscoManager.getInstance();
        HttpCallBean getNewHttpCallBean2 = discoManager2.getNewHttpCallBean("87.248.113.14");
        discoManager2 = discoManager2;


        discoManager2.setDiscoFaultControllerJMXMBeanAttrbiute("DetailedFaults", "false");

        getNewHttpCallBean2.setServiceName("Baseline", "discoBaseline");

        getNewHttpCallBean2.setVersion("v2");

        Map map3 = new HashMap();
        map3.put("Username","foo");
        map3.put("Unknown","bar");
        getNewHttpCallBean2.setAuthCredentials(map3);

        getNewHttpCallBean2.setPostObjectForRequestType(createAsDocument1, "SOAP");


        Timestamp getTimeAsTimeStamp9 = new Timestamp(System.currentTimeMillis());

        discoManager2.makeSoapDiscoHTTPCalls(getNewHttpCallBean2);

        XMLHelpers xMLHelpers5 = new XMLHelpers();
        Document createAsDocument11 = xMLHelpers5.getXMLObjectFromString("<response><identities><Ident><principal>PRINCIPAL: Username</principal><credentialName>CREDENTIAL: Username</credentialName><credentialValue>foo</credentialValue></Ident></identities></response>");



        HttpResponseBean getResponseObjectsByEnum13 = getNewHttpCallBean2.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.SOAP);
        AssertionUtils.multiAssertEquals(createAsDocument11, getResponseObjectsByEnum13.getResponseObject());

        Map<String, String> map7 = getResponseObjectsByEnum13.getFlattenedResponseHeaders();
        AssertionUtils.multiAssertEquals("Username:foo", map7.get("Credentials"));

        // generalHelpers.pauseTest(1000L);


        discoManager2.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp9, new RequestLogRequirement("2.8", "testIdentityChain") );

        discoManager2.verifyAccessLogEntriesAfterDate(getTimeAsTimeStamp9, new AccessLogRequirement(null, null, "Ok") );
    }

}
