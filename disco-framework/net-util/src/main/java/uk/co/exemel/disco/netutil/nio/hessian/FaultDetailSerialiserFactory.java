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

import uk.co.exemel.disco.core.api.fault.FaultDetail;
import uk.co.exemel.disco.core.api.transcription.TranscribableParams;
import com.caucho.hessian.io.AbstractSerializerFactory;
import com.caucho.hessian.io.Deserializer;
import com.caucho.hessian.io.HessianProtocolException;
import com.caucho.hessian.io.Serializer;

import java.util.Set;

/**
 * classes without a no parameter constructor use the DiscoInternalDeserialiser - this serialiser/deserialiser requires the client and server to agree on
 * the serialisation (same number of properties in the same order)
 * </p>
 * classes with a no parameter constructor use the TranscribableSerialiser/Deserialiser which allows the client and server to vary in number and order of serialised
 * properties
 * </p>
 * classes using the TranscribableSerialiser/deserialiser are constrained in the transcription they can perform.  Namely they must advertise the params they will
 * transcribe via the getParameters method, and only those parameters in the same order may be transcribel
 * </p>
 * The Disco class FaultDetail does not conform to the above requirements so must use the InternalSerialiser/Deserialiser
 */
public class FaultDetailSerialiserFactory extends AbstractSerializerFactory {

    private Set<TranscribableParams> transcriptionParams;

    public FaultDetailSerialiserFactory(Set<TranscribableParams> transcriptionParams) {
        this.transcriptionParams = transcriptionParams;
    }

    @Override
	public Deserializer getDeserializer(Class cls) throws HessianProtocolException {

		Deserializer deserializer = null;
        if (FaultDetail.class.isAssignableFrom(cls)) {
            deserializer = new FaultDetailDeserialiser(cls, transcriptionParams);
        }

		return deserializer;
	}

	@Override
	public Serializer getSerializer(Class cls) throws HessianProtocolException {

		Serializer serializer = null;

        if (FaultDetail.class.isAssignableFrom(cls)) {
            serializer = new FaultDetailSerialiser(transcriptionParams);
        }

		return serializer;
	}

}
