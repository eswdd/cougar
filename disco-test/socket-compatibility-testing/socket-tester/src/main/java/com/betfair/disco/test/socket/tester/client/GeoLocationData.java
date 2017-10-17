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

import uk.co.exemel.disco.api.geolocation.GeoLocationDetails;

import java.util.Arrays;
import java.util.List;

/**
*
*/
class GeoLocationData implements GeoLocationDetails {


    @Override
    public String getRemoteAddr() {
        return "1.2.3.4";
    }

    @Override
    public List<String> getResolvedAddresses() {
        return Arrays.asList("1.2.3.4", "127.0.0.1");
    }

    @Override
    public String getCountry() {
        return "UK";
    }

    @Override
    public boolean isLowConfidenceGeoLocation() {
        return false;
    }

    @Override
    public String getLocation() {
        return "Berlin";
    }

    @Override
    public String getInferredCountry() {
        return "DE";
    }
}
