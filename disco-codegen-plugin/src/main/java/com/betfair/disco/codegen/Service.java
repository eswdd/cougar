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

package uk.co.exemel.disco.codegen;

public class Service {

    /**
     * name of service.
     */
    private String serviceName;

    /**
     * package name service belongs to
     */
    private String packageName;

    /**
     * directory to output resultant IDD to
     */
    private String outputDir = "target/generated-resources/idd";

    public String getServiceName() {
		return serviceName;
	}

	public String getPackageName() {
		return packageName;
	}

    public String getOutputDir() {
        return outputDir;
    }
}
