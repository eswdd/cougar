/*
 * Copyright 2014, Simon Matić Langford
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

import javax.ws.rs.core.MediaType;
import java.util.logging.Level;


@SuppressWarnings("serial")
public class DiscoMarshallingException extends DiscoException {
	private static final Level LOG_LEVEL = Level.FINE;
    private final String format;

    private DiscoMarshallingException(ServerFaultCode fault, String format, String message, Throwable t) {
		super(LOG_LEVEL, fault, format + ": "+message,t);
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public static DiscoMarshallingException marshallingException(String format, String message, Throwable t, boolean client) {
        return new DiscoMarshallingException(client ? ServerFaultCode.ClientSerialisationFailure : ServerFaultCode.ServerSerialisationFailure, format, message, t);
    }

    public static DiscoMarshallingException marshallingException(String format, Throwable t, boolean client) {
        return marshallingException(format, "", t, client);
    }

    public static DiscoMarshallingException unmarshallingException(String format, String message, Throwable t, boolean client) {
        return new DiscoMarshallingException(client ? ServerFaultCode.ClientDeserialisationFailure : ServerFaultCode.ServerDeserialisationFailure, format, message, t);
    }

    public static DiscoMarshallingException unmarshallingException(String format, Throwable t, boolean client) {
        return unmarshallingException(format, t.getMessage(), t, client);
    }

    public static DiscoMarshallingException unmarshallingException(String format, String message, boolean client) {
        return unmarshallingException(format, message, null, client);
    }

}
