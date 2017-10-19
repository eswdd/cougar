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

// Originally from UpdatedComponentTests/ResponseTypes/REST/Rest_Post_SetResponse_JSON_BlankEntry.xls;
package uk.co.exemel.disco.tests.updatedcomponenttests.responsetypes.rest;

import uk.co.exemel.testing.utils.disco.misc.XMLHelpers;
import uk.co.exemel.testing.utils.JSONHelpers;
import uk.co.exemel.testing.utils.disco.assertions.AssertionUtils;
import uk.co.exemel.testing.utils.disco.beans.HttpCallBean;
import uk.co.exemel.testing.utils.disco.beans.HttpResponseBean;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageContentTypeEnum;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolRequestTypeEnum;
import uk.co.exemel.testing.utils.disco.enums.DiscoMessageProtocolResponseTypeEnum;
import uk.co.exemel.testing.utils.disco.manager.DiscoManager;
import uk.co.exemel.testing.utils.disco.manager.RequestLogRequirement;

import org.json.JSONObject;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensure that when a Rest (JSON) Post operation is performed against Disco, passing a Set with a blank entry in the post body, it is correctly de-serialized, processed, returned the correct response.
 */
public class RestPostSetResponseJSONBlankEntryTest {
    @Test
    public void doTest() throws Exception {

        DiscoManager discoManager1 = DiscoManager.getInstance();
        HttpCallBean getNewHttpCallBean1 = discoManager1.getNewHttpCallBean("87.248.113.14");
        discoManager1 = discoManager1;


        getNewHttpCallBean1.setOperationName("TestSimpleSetGet", "simpleSetGet");

        getNewHttpCallBean1.setServiceName("baseline", "discoBaseline");

        getNewHttpCallBean1.setVersion("v2");

        Map map2 = new HashMap();
        map2.put("RESTJSON","{\"inputSet\": \n           [\"bbb string\",\n        \"\",\n        \"ddd string\",\n        \"ccc string\"]\n}");
        getNewHttpCallBean1.setPostQueryObjects(map2);


        Timestamp getTimeAsTimeStamp7 = new Timestamp(System.currentTimeMillis());

        discoManager1.makeRestDiscoHTTPCall(getNewHttpCallBean1, DiscoMessageProtocolRequestTypeEnum.RESTJSON, DiscoMessageContentTypeEnum.XML);

        discoManager1.makeRestDiscoHTTPCall(getNewHttpCallBean1, DiscoMessageProtocolRequestTypeEnum.RESTJSON, DiscoMessageContentTypeEnum.JSON);

        XMLHelpers xMLHelpers4 = new XMLHelpers();
        Document createAsDocument10 = xMLHelpers4.getXMLObjectFromString("<TestSimpleSetGetResponse><String>bbb string</String><String/><String>ddd string</String><String>ccc string</String></TestSimpleSetGetResponse>");

        JSONHelpers jSONHelpers5 = new JSONHelpers();
        JSONObject createAsJSONObject11 = jSONHelpers5.createAsJSONObject(new JSONObject("{ \"response\":[\"bbb string\",\"\",\"ddd string\",\"ccc string\"]}"));

        HttpResponseBean response6 = getNewHttpCallBean1.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONXML);
        AssertionUtils.multiAssertEquals(createAsDocument10, (Document)response6.getResponseObject(),"/*[local-name()='TestSimpleSetGetResponse']");
        AssertionUtils.multiAssertEquals((int) 200, response6.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response6.getHttpStatusText());

        HttpResponseBean response7 = getNewHttpCallBean1.getResponseObjectsByEnum(DiscoMessageProtocolResponseTypeEnum.RESTJSONJSON);
        AssertionUtils.multiAssertEquals(createAsJSONObject11, (JSONObject)response7.getResponseObject(), "/response");
        AssertionUtils.multiAssertEquals((int) 200, response7.getHttpStatusCode());
        AssertionUtils.multiAssertEquals("OK", response7.getHttpStatusText());

        // generalHelpers.pauseTest(500L);


        discoManager1.verifyRequestLogEntriesAfterDate(getTimeAsTimeStamp7, new RequestLogRequirement("2.8", "testSimpleSetGet"),new RequestLogRequirement("2.8", "testSimpleSetGet") );
    }

}