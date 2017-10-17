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

package uk.co.exemel.disco.core.impl.logging;

import uk.co.exemel.disco.api.LoggableEvent;
import uk.co.exemel.disco.core.api.ev.Subscription;

import java.util.Date;

/**
 *
 */
public class ConnectedObjectLogEvent implements LoggableEvent {

    private String logName;
    private Date eventTime;
    private String subscriptionId;
    private String heapUri;
    private String closeReason;

    public ConnectedObjectLogEvent(String logName, Date eventTime, String subscriptionId, String heapUri, String closeReason) {
        this.logName = logName;
        this.eventTime = eventTime;
        this.subscriptionId = subscriptionId;
        this.heapUri = heapUri;
        this.closeReason = closeReason;
    }

    @Override
    public Object[] getFieldsToLog() {
        return new Object[] {
            eventTime,
            subscriptionId,
            heapUri,
            closeReason
        };
    }

    @Override
    public String getLogName() {
        return logName;
    }
}
