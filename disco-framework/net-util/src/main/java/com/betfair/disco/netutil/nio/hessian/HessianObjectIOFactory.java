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

package uk.co.exemel.disco.netutil.nio.hessian;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import uk.co.exemel.disco.core.api.transcription.TranscribableParams;
import uk.co.exemel.disco.netutil.nio.DiscoProtocol;
import uk.co.exemel.disco.transport.api.protocol.DiscoObjectIOFactory;
import uk.co.exemel.disco.transport.api.protocol.DiscoObjectInput;
import uk.co.exemel.disco.transport.api.protocol.DiscoObjectOutput;
import com.caucho.hessian.io.SerializerFactory;

public class HessianObjectIOFactory implements DiscoObjectIOFactory {

    private Map<Byte, DiscoSerializerFactory> protocolSerializerFactories;

	public HessianObjectIOFactory(boolean client) {
        protocolSerializerFactories = new HashMap<Byte, DiscoSerializerFactory>();
        for (byte b = DiscoProtocol.TRANSPORT_PROTOCOL_VERSION_MIN_SUPPORTED; b<=DiscoProtocol.TRANSPORT_PROTOCOL_VERSION_MAX_SUPPORTED; b++) {
            Set<TranscribableParams> transcriptionParams = DiscoProtocol.getTranscribableParamSet(b);
            DiscoSerializerFactory csf = DiscoSerializerFactory.createInstance(transcriptionParams);
            csf.setAllowNonSerializable(true);
            csf.addFactory(new TranscribableSerialiserFactory(transcriptionParams, client));
            csf.addFactory(new EnumSerialiserFactory(transcriptionParams));
            csf.addFactory(new FaultDetailSerialiserFactory(transcriptionParams));
            protocolSerializerFactories.put(b, csf);
        }
	}


	@Override
	public DiscoObjectInput newDiscoObjectInput(InputStream is, byte protocolVersion) {
        return new HessianObjectInput(is, protocolSerializerFactories.get(protocolVersion));
	}

	@Override
	public DiscoObjectOutput newDiscoObjectOutput(OutputStream os, byte protocolVersion) {
        return new HessianObjectOutput(os, protocolSerializerFactories.get(protocolVersion));
	}


}
