/*
 * Copyright 2014, The Sporting Exchange Limited
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

package uk.co.exemel.disco.transport.jetty;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Response;

public class MockJettyResponse extends Response {
	private int status = 0;
	private final Map<String, String> overrides =  new HashMap<String, String>();

	public MockJettyResponse() {
		super(null, null);
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public void setStatus(int sc) {
		status = sc;
	}

	@Override
	public void setContentType(String contentType) {
		overrides.put("Content-Type", contentType);
	}
}
