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

package uk.co.exemel.disco.core.api.builder;

import uk.co.exemel.disco.api.RequestUUID;
import uk.co.exemel.disco.api.geolocation.GeoLocationDetails;

import java.util.BitSet;
import java.util.Date;

/**
 * Base utils for building ExecutionContexts
 */
public abstract class BaseExecutionContextBuilder<T extends BaseExecutionContextBuilder> {

    private BitSet whatsSet = new BitSet(MAX_BASE_BITS+getNumSpecificComponents());

    protected GeoLocationDetails location;
    protected RequestUUID uuid;
    protected Date receivedTime;
    protected Date requestTime;
    protected boolean traceLoggingEnabled;
    protected int transportSecurityStrengthFactor;

    protected abstract int getNumSpecificComponents();
    protected final static int MAX_BASE_BITS = 6;

    public T setLocation(GeoLocationDetails location) {
        this.location = location;
        set(0);
        return (T) this;
    }

    public T setRequestUUID(RequestUUID uuid) {
        this.uuid = uuid;
        set(1);
        return (T) this;
    }

    public T setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
        set(2);
        return (T) this;
    }

    public T setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
        set(3);
        return (T) this;
    }

    public T setTraceLoggingEnabled(boolean traceLoggingEnabled) {
        this.traceLoggingEnabled = traceLoggingEnabled;
        set(4);
        return (T) this;
    }

    public T setTransportSecurityStrengthFactor(int factor) {
        transportSecurityStrengthFactor = factor;
        set(5);
        return (T) this;
    }

    protected void beenSet(int subComponent) {
        set(MAX_BASE_BITS + subComponent);
    }

    private void set(int bit) {
        if (whatsSet.get(bit)) {
            throw new IllegalStateException("Component has already been set");
        }
        whatsSet.set(bit);
    }

    protected void checkReady() {
        if (whatsSet.nextClearBit(0) <= getNumSpecificComponents()) {
            throw new IllegalStateException("Not all components have been set on this execution context");
        }
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public Object getRequestUUID() {
        return uuid;
    }
}
