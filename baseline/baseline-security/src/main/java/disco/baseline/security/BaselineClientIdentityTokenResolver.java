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

package uk.co.exemel.disco.baseline.security;

import uk.co.exemel.disco.api.security.IdentityToken;
import uk.co.exemel.disco.client.api.HttpClientIdentityTokenResolver;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpUriRequest;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code BaselineClientIdentityTokenResolver}
 */
public class BaselineClientIdentityTokenResolver implements HttpClientIdentityTokenResolver {

    private static final String TOKEN_PREFIX = "X-Token-";

    @Override
    public List<IdentityToken> resolve(HttpUriRequest input, X509Certificate[] certificateChain) {
        List<IdentityToken> credentials = new ArrayList<IdentityToken>();

        for (SimpleIdentityTokenName securityToken : SimpleIdentityTokenName.values()) {

            Header authHeaderValue = input.getFirstHeader(TOKEN_PREFIX + securityToken.name());
            if (authHeaderValue != null) {
                credentials.add(new IdentityToken(securityToken.name(), authHeaderValue.getValue()));
            }
        }

        return credentials;
    }

    @Override
    public void rewrite(List<IdentityToken> credentials, HttpUriRequest output) {
        if (credentials != null) {
            for (IdentityToken token : credentials) {
                try {
                    String tokenName = SimpleIdentityTokenName.valueOf(token.getName()).name();
                    output.setHeader(TOKEN_PREFIX + tokenName, token.getValue());
                } catch (IllegalArgumentException e) { /*ignore*/ }
            }
        }
    }

    @Override
    public boolean isRewriteSupported() {
        return true;
    }
}
