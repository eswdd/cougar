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

package uk.co.exemel.disco.transport.api;

import uk.co.exemel.disco.core.api.builder.DehydratedExecutionContextBuilder;

import java.util.Set;

public interface DehydratedExecutionContextResolver<Transport, CredentialsContainer> {
    DehydratedExecutionContextComponent[] supportedComponents();
    void resolving(Set<DehydratedExecutionContextComponent> handling);
    void resolve(Transport transport, CredentialsContainer credentialsContainer, DehydratedExecutionContextBuilder builder);

}
