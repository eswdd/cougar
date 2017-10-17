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

package uk.co.exemel.disco.core.api.exception;

import java.util.logging.Level;


/**
 * An error in the processing of a request has been detected - other requests may still succeed.
 *
 */
@SuppressWarnings("serial")
public class DiscoFrameworkException extends DiscoException {
	private static final Level LOG_LEVEL = Level.WARNING;

	public DiscoFrameworkException(String cause) {
		super(LOG_LEVEL, ServerFaultCode.FrameworkError, cause);
	}

	public DiscoFrameworkException(String cause, Throwable t) {
		this(ServerFaultCode.FrameworkError, cause, t);
	}

	public DiscoFrameworkException(ServerFaultCode faultCode, String cause) {
		super(LOG_LEVEL, faultCode, cause);
	}

	public DiscoFrameworkException(ServerFaultCode faultCode, String cause, Throwable t) {
		super(LOG_LEVEL, faultCode, cause, t);
	}



}
