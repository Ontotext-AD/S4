/*
* Copyright (c) 2015
*
* This file is part of the s4.ontotext.com REST client library, and is
* licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.ontotext.s4.api.restclient;

import com.ontotext.s4.api.util.Preconditions;
import com.ontotext.s4.service.S4ServiceClient;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A builder class enabling easy configuration of the S4ServiceClient
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-02-19.
 */
public final class S4ClientBuilder {

    private String s4Endpoint;

    private String apiKeyId;

    private String apiPassword;

    public static S4ClientBuilder newClientInstance() {
        return new S4ClientBuilder();
    }

    public S4ClientBuilder withApiKeyId(String apiKeyId) {
        Preconditions.notNull(apiKeyId, "API key id");
        this.apiKeyId = apiKeyId;
        return this;
    }

    public S4ClientBuilder withApiPassword(String apiPassword) {
        Preconditions.notNull(apiPassword, "API password");
        this.apiPassword = apiPassword;
        return this;
    }

    public S4ClientBuilder withS4Endpoint(String s4Endpoint) {
        Preconditions.notNull(s4Endpoint, "S4 endpoint");
        this.s4Endpoint = s4Endpoint;
        return this;
    }

    public S4ServiceClient build() {
        return new S4ServiceClient(getServiceUrl(), this.apiKeyId, this.apiPassword);
    }

    private URL getServiceUrl() {
        try {
            return new URL(this.s4Endpoint);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
