package com.ontotext.s4.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.ontotext.s4.catalog.ServiceDescriptor;
import com.ontotext.s4.client.HttpClient;
import com.ontotext.s4.client.HttpClientException;
import com.ontotext.s4.service.S4AbstractClient;
import com.ontotext.s4.service.util.S4ServiceClientException;

import java.net.MalformedURLException;
import java.net.URL;

public class S4AbstractClientImpl implements S4AbstractClient {

    protected HttpClient client;
    protected boolean requestCompression;

    private S4AbstractClientImpl() {}
    
    public S4AbstractClientImpl(ServiceDescriptor item, String apiKeyId, String apiPassword) {
        URL endpoint;
        try {
            endpoint = new URL(item.getServiceUrl());
        } catch (MalformedURLException murle) {
            throw new IllegalArgumentException("Invalid ServiceDescriptor specified. No API endpoint found.", murle);
        }
        this.client = new HttpClient(endpoint, apiKeyId, apiPassword);
    }

    public S4AbstractClientImpl(URL endpoint, String apiKeyId, String apiPassword) {
        this.client = new HttpClient(endpoint, apiKeyId, apiPassword);
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
}
