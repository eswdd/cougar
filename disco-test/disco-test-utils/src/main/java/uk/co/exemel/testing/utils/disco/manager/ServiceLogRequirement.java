/*
 * Copyright 2013, The Sporting Exchange Limited
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

package uk.co.exemel.testing.utils.disco.manager;

/**
 * a
 */
public class ServiceLogRequirement implements LogTailer.LogRequirement {
    String message;
    boolean containsCheck;

    public ServiceLogRequirement(String message) {
        this.message = message;
    }

    public ServiceLogRequirement(String message, boolean containsCheck) {
        this.message = message;
        this.containsCheck = containsCheck;
    }

    @Override
    public String toString() {
        return "ServiceLogRequirement{" +
                "message='" + message + '\'' +
                ", containsCheck=" + containsCheck +
                '}';
    }
}
