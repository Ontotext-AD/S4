/*
 * S4 Java client library
 * Copyright 2016 Ontotext AD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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

package com.ontotext.s4.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.ontotext.s4.catalog.ServiceDescriptor;
import com.ontotext.s4.client.HttpClient;
import com.ontotext.s4.client.HttpClientException;
import com.ontotext.s4.common.Parameters;
import com.ontotext.s4.service.S4AbstractClient;
import com.ontotext.s4.service.util.ResponseFormat;
import com.ontotext.s4.service.util.S4ServiceClientException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public abstract class S4AbstractClientImpl implements S4AbstractClient {

    protected HttpClient client;
    protected boolean requestCompression;

    
    public S4AbstractClientImpl(ServiceDescriptor item, String apiKeyId, String keySecret) {
        URL endpoint;
        try {
            endpoint = new URL(item.getServiceUrl());
        } catch (MalformedURLException murle) {
            throw new IllegalArgumentException("Invalid ServiceDescriptor specified. No API endpoint found.", murle);
        }
        this.client = new HttpClient(endpoint, apiKeyId, keySecret);
    }

    public S4AbstractClientImpl(URL endpoint, String apiKeyId, String keySecret) {
        this.client = new HttpClient(endpoint, apiKeyId, keySecret);
    }

    public void setRequestCompression(boolean requestCompression) {
        this.requestCompression = requestCompression;
    }

    /**
     * Processes a specific {@link HttpClientException} and returns its message.
     * *NOTE* This is a utility helper method
     * @param e An {@link HttpClientException} to process
     * @return The error message from the exception
     */
    protected JsonNode handleErrors(HttpClientException e) {
        JsonNode response = e.getResponse();
        if(response == null) {
            throw new S4ServiceClientException(e.getMessage(), e);
        }
        return response.get("message");
    }

    /**
     * Construct headers Map to be sent with the request
     * @param serializationFormat A response type accepted by the S4 API
     * @return A map of all request headers
     */
    protected Map<String, String> constructHeaders(ResponseFormat serializationFormat) {
        Map<String, String> headers =
                Parameters.newInstance().withValue("Accept", serializationFormat.acceptHeader);

        if(requestCompression) {
            headers.put("Accept-Encoding", "gzip");
        }
        return headers;
    }
}
