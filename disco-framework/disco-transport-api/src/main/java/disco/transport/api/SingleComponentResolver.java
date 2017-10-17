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

import java.util.HashSet;
import java.util.Set;

/**
 * Handy base class for resolvers which handle only a single component.
 */
public abstract class SingleComponentResolver<Transport,Body> implements DehydratedExecutionContextResolver<Transport,Body> {
    private final DehydratedExecutionContextComponent component;

    protected SingleComponentResolver(DehydratedExecutionContextComponent component) {
        this.component = component;
    }

    @Override
    public void resolving(Set<DehydratedExecutionContextComponent> handling) {
        if (handling.size() > 1 || (handling.size() == 1 && !handling.contains(component))) {
            Set<DehydratedExecutionContextComponent> cantHandle = new HashSet<>(handling);
            cantHandle.remove(component);
            throw new IllegalArgumentException("I don't know how to handle: "+cantHandle);
        }
    }

    @Override
    public DehydratedExecutionContextComponent[] supportedComponents() {
        return new DehydratedExecutionContextComponent[] { component };
    }
}
