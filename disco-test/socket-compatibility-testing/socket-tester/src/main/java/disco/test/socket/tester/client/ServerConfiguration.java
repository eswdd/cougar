/*
 * Copyright 2015, Simon Matić Langford
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

package uk.co.exemel.disco.test.socket.tester.client;

/**
 *
 */
public class ServerConfiguration {
    private int port;
    private int minProtocolVersion;
    private int maxProtocolVersion;
    private String variant;

    public ServerConfiguration(int port, int minProtocolVersion, int maxProtocolVersion, String variant) {
        this.port = port;
        this.minProtocolVersion = minProtocolVersion;
        this.maxProtocolVersion = maxProtocolVersion;
        this.variant = variant;
    }

    public int getPort() {
        return port;
    }

    public int getMinProtocolVersion() {
        return minProtocolVersion;
    }

    public int getMaxProtocolVersion() {
        return maxProtocolVersion;
    }

    public String getVariant() {
        return variant;
    }
}
