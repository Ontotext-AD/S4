/*
 * S4 Java client library
 * Copyright (c) 2014, Ontotext AD, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.ontotext.s4.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Client responsible for communication with the S4 API. Handles
 * authentication, and serialization and deserialization of JSON request
 * and response bodies.
 * 
 */
public class HttpClient {

  private final ObjectMapper MAPPER = new ObjectMapper().disable(
          DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .setInjectableValues(
                  new InjectableValues.Std().addValue(HttpClient.class, this));

  private final XmlMapper XML_MAPPER = (XmlMapper)new XmlMapper().disable(
          DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .setInjectableValues(
                  new InjectableValues.Std().addValue(HttpClient.class, this));

  /**
   * The standard base URI for the S4 API.
   */
  public static final URL DEFAULT_BASE_URL;
  static {
    try {
      DEFAULT_BASE_URL = new URL("https://text.s4.ontotext.com/");
    } catch(MalformedURLException e) {
      // can't happen
      throw new ExceptionInInitializerError(e);
    }
  }

  /**
   * The HTTP basic authentication header that will be appended to all
   * requests.
   */
  private String authorizationHeader;

  /**
   * The base URL that will be used to resolve any relative request
   * URIs.
   */
  private URL baseUrl;

  /**
   * Create a client that uses the {@link #DEFAULT_BASE_URL default base
   * URL}.
   * 
   * @param apiKeyId API key identifier for authentication
   * @param apiPassword API key password
   */
  public HttpClient(String apiKeyId, String apiPassword) {
    this(DEFAULT_BASE_URL, apiKeyId, apiPassword);
  }

  /**
   * Create a client using a specified base URL (for advanced use only -
   * the default URL will work for all normal cases).
   * 
   * @param url API base URL
   * @param apiKeyId API key identifier for authentication
   * @param apiPassword API key password
   */
  public HttpClient(URL url, String apiKeyId, String apiPassword) {
    baseUrl = url;
    try {
      // HTTP header is "Basic base64(username:password)"
      authorizationHeader =
              "Basic "
                      + DatatypeConverter
                              .printBase64Binary((apiKeyId + ":" + apiPassword)
                                      .getBytes("UTF-8"));
    } catch(UnsupportedEncodingException e) {
      // should never happen
      throw new RuntimeException("JVM claims not to support UTF-8 encoding...",
              e);
    }
  }

  public URL getBaseUrl() {
    return baseUrl;
  }

  /**
   * Make an API request and parse the JSON response into a new object.
   * 
   * @param target the URL to request (relative URLs will resolve
   *          against the {@link #getBaseUrl() base URL}).
   * @param method the request method (GET, POST, DELETE, etc.)
   * @param responseType the Java type corresponding to a successful
   *          response message for this URL
   * @param requestBody the object that should be serialized to JSON as
   *          the request body. If <code>null</code> no request body is
   *          sent
   * @param extraHeaders any additional HTTP headers, specified as an
   *          alternating sequence of header names and values
   * @return for a successful response, the deserialized response body,
   *         or <code>null</code> for a 201 response
   * @throws HttpClientException if an exception occurs during
   *           processing, or the server returns a 4xx or 5xx error
   *           response (in which case the response JSON message will be
   *           available as a {@link JsonNode} in the exception).
   */
  public <T> T request(String target, String method,
          TypeReference<T> responseType, Object requestBody,
          String... extraHeaders) throws HttpClientException {
    try {
      HttpURLConnection connection =
              sendRequest(target, method, requestBody, extraHeaders);
      return readResponseOrError(connection, responseType);
    } catch(IOException e) {
      throw new HttpClientException(e);
    }
  }

  /**
   * Make an API request and return the raw data from the response as an
   * InputStream.
   * 
   * @param target the URL to request (relative URLs will resolve
   *          against the {@link #getBaseUrl() base URL}).
   * @param method the request method (GET, POST, DELETE, etc.)
   * @param requestBody the object that should be serialized to JSON as
   *          the request body. If <code>null</code> no request body is
   *          sent
   * @param extraHeaders any additional HTTP headers, specified as an
   *          alternating sequence of header names and values
   * @return for a successful response, the response stream, or
   *         <code>null</code> for a 201 response
   * @throws HttpClientException if an exception occurs during
   *           processing, or the server returns a 4xx or 5xx error
   *           response (in which case the response JSON message will be
   *           available as a {@link JsonNode} in the exception).
   */
  public InputStream requestForStream(String target, String method,
          Object requestBody, String... extraHeaders)
          throws HttpClientException {
    try {
      HttpURLConnection connection =
              sendRequest(target, method, requestBody, extraHeaders);
      int responseCode = connection.getResponseCode();
      if(responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
        // successful response with no content
        return null;
      } else if(responseCode >= 400) {
        readError(connection);
        return null; // not reachable, readError always throws exception
      } else if(responseCode >= 300) {
        // redirect - all redirects we care about from the S4
        // APIs are 303. We have to follow them manually to make
        // authentication work properly.
        String location = connection.getHeaderField("Location");
        // consume body
        InputStream stream = connection.getInputStream();
        IOUtils.copy(stream, new NullOutputStream());
        IOUtils.closeQuietly(stream);
        // follow the redirect
        return requestForStream(location, method, requestBody, extraHeaders);
      } else {
        return connection.getInputStream();
      }
    } catch(IOException e) {
      throw new HttpClientException(e);
    }

  }

  /**
   * Make an API request and parse the JSON response, using the response
   * to update the state of an existing object.
   * 
   * @param target the URL to request (relative URLs will resolve
   *          against the {@link #getBaseUrl() base URL}).
   * @param method the request method (GET, POST, DELETE, etc.)
   * @param responseObject the Java object to update from a successful
   *          response message for this URL
   * @param requestBody the object that should be serialized to JSON as
   *          the request body. If <code>null</code> no request body is
   *          sent
   * @param extraHeaders any additional HTTP headers, specified as an
   *          alternating sequence of header names and values
   * @throws HttpClientException if an exception occurs during
   *           processing, or the server returns a 4xx or 5xx error
   *           response (in which case the response JSON message will be
   *           available as a {@link JsonNode} in the exception).
   */
  public void requestForUpdate(String target, String method,
          Object responseObject, Object requestBody, String... extraHeaders)
          throws HttpClientException {
    try {
      HttpURLConnection connection =
              sendRequest(target, method, requestBody, extraHeaders);
      readResponseOrErrorForUpdate(connection, responseObject);
    } catch(IOException e) {
      throw new HttpClientException(e);
    }
  }

  /**
   * Handles the sending side of an HTTP request, returning a connection
   * from which the response (or error) can be read.
   */
  private HttpURLConnection sendRequest(String target, String method,
          Object requestBody, String... extraHeaders) throws IOException {
    URL requestUrl = new URL(baseUrl, target);
    HttpURLConnection connection =
            (HttpURLConnection)requestUrl.openConnection();
    connection.setRequestMethod(method);
    connection.setInstanceFollowRedirects(false);
    connection.setRequestProperty("Authorization", authorizationHeader);
    boolean sentAccept = false;
    if(extraHeaders != null) {
      for(int i = 0; i < extraHeaders.length; i++) {
        if("Accept".equals(extraHeaders[i])) sentAccept = true;
        connection.setRequestProperty(extraHeaders[i], extraHeaders[++i]);
      }
    }
    if(!sentAccept)
      connection.setRequestProperty("Accept", "application/json");
    if(requestBody != null) {
      connection.setDoOutput(true);
      connection.setRequestProperty("Content-Type", "application/json");
      OutputStream out = connection.getOutputStream();
      try {
        MAPPER.writeValue(out, requestBody);
      } finally {
        out.close();
      }
    }
    return connection;
  }

  /**
   * Read a response or error message from the given connection,
   * handling any 303 redirect responses.
   */
  private <T> T readResponseOrError(HttpURLConnection connection,
          TypeReference<T> responseType) throws HttpClientException {
    return readResponseOrError(connection, responseType, true);
  }

  /**
   * Read a response or error message from the given connection,
   * handling any 303 redirect responses if <code>followRedirects</code>
   * is true.
   */
  private <T> T readResponseOrError(HttpURLConnection connection,
          TypeReference<T> responseType, boolean followRedirects)
          throws HttpClientException {
    InputStream stream = null;
    try {
      int responseCode = connection.getResponseCode();
      if(responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
        // successful response with no content
        return null;
      }
      String encoding = connection.getContentEncoding();
      if("gzip".equalsIgnoreCase(encoding)) {
        stream = new GZIPInputStream(connection.getInputStream());
      } else {
        stream = connection.getInputStream();
      }

      if(responseCode < 300 || responseCode >= 400 || !followRedirects) {
        try {
          return MAPPER.readValue(stream, responseType);
        } finally {
          stream.close();
        }
      } else {
        // redirect - all redirects we care about from the S4
        // APIs are 303. We have to follow them manually to make
        // authentication work properly.
        String location = connection.getHeaderField("Location");
        // consume body
        IOUtils.copy(stream, new NullOutputStream());
        IOUtils.closeQuietly(stream);
        // follow the redirect
        return get(location, responseType);
      }
    } catch(Exception e) {
      readError(connection);
      return null; // unreachable, as readError always throws exception
    }
  }

  /**
   * Read a response or error message from the given connection, and
   * update the state of the given object.
   */
  private void readResponseOrErrorForUpdate(HttpURLConnection connection,
          Object responseObject) throws HttpClientException {
    InputStream stream = null;
    try {
      if(connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
        // successful response with no content
        return;
      }
      stream = connection.getInputStream();
      try {
        MAPPER.readerForUpdating(responseObject).readValue(stream);
      } finally {
        stream.close();
      }
    } catch(Exception e) {
      readError(connection);
    }
  }

  /**
   * Read an error response from the given connection and throw a
   * suitable {@link HttpClientException}. This method always throws an
   * exception, it will never return normally.
   */
  private void readError(HttpURLConnection connection)
          throws HttpClientException {
    InputStream stream;
    try {
      String encoding = connection.getContentEncoding();
      if("gzip".equalsIgnoreCase(encoding)) {
        stream = new GZIPInputStream(connection.getInputStream());
      } else {
        stream = connection.getInputStream();
      }

      InputStreamReader reader = new InputStreamReader(stream, "UTF-8");

      try {
        JsonNode errorNode = null;
        if(connection.getContentType().contains("json")) {
          errorNode = MAPPER.readTree(stream);
        } else if(connection.getContentType().contains("xml")) {
          errorNode = XML_MAPPER.readTree(stream);
        }

        throw new HttpClientException("Server returned response code "
                + connection.getResponseCode(), errorNode);

      } finally {
        reader.close();
      }
    } catch(HttpClientException e2) {
      throw e2;
    } catch(Exception e2) {
      throw new HttpClientException("Error communicating with server", e2);
    }
  }

  /**
   * Perform an HTTP GET request, parsing the JSON response to create a
   * new object.
   * 
   * @param target the URL to request (relative URLs will resolve
   *          against the {@link #getBaseUrl() base URL}).
   * @param responseType the Java type corresponding to a successful
   *          response message for this URL
   * @return for a successful response, the deserialized response body,
   *         or <code>null</code> for a 201 response
   * @throws HttpClientException if an exception occurs during
   *           processing, or the server returns a 4xx or 5xx error
   *           response (in which case the response JSON message will be
   *           available as a {@link JsonNode} in the exception).
   */
  public <T> T get(String target, TypeReference<T> responseType)
          throws HttpClientException {
    return request(target, "GET", responseType, null);
  }

  /**
   * Perform an HTTP GET request, parsing the JSON response to update
   * the state of an existing object.
   * 
   * @param target the URL to request (relative URLs will resolve
   *          against the {@link #getBaseUrl() base URL}).
   * @param responseObject the Java object to update from a successful
   *          response message for this URL
   * @throws HttpClientException if an exception occurs during
   *           processing, or the server returns a 4xx or 5xx error
   *           response (in which case the response JSON message will be
   *           available as a {@link JsonNode} in the exception).
   */
  public void getForUpdate(String target, Object responseObject)
          throws HttpClientException {
    requestForUpdate(target, "GET", responseObject, null);
  }

  /**
   * Perform an HTTP POST request, parsing the JSON response to create a
   * new object.
   * 
   * @param target the URL to request (relative URLs will resolve
   *          against the {@link #getBaseUrl() base URL}).
   * @param responseType the Java type corresponding to a successful
   *          response message for this URL
   * @param requestBody the object that should be serialized to JSON as
   *          the request body. POST requests require a request body, so
   *          this parameter must not be <code>null</code>
   * @return for a successful response, the deserialized response body,
   *         or <code>null</code> for a 201 response
   * @throws HttpClientException if an exception occurs during
   *           processing, or the server returns a 4xx or 5xx error
   *           response (in which case the response JSON message will be
   *           available as a {@link JsonNode} in the exception).
   */
  public <T> T post(String target, TypeReference<T> responseType,
          Object requestBody) throws HttpClientException {
    return request(target, "POST", responseType, requestBody);
  }

  /**
   * Perform an HTTP POST request, parsing the JSON response to update
   * the state of an existing object.
   * 
   * @param target the URL to request (relative URLs will resolve
   *          against the {@link #getBaseUrl() base URL}).
   * @param responseObject the Java object to update from a successful
   *          response message for this URL
   * @param requestBody the object that should be serialized to JSON as
   *          the request body. POST requests require a request body, so
   *          this parameter must not be <code>null</code>
   * @throws HttpClientException if an exception occurs during
   *           processing, or the server returns a 4xx or 5xx error
   *           response (in which case the response JSON message will be
   *           available as a {@link JsonNode} in the exception).
   */
  public void postForUpdate(String target, Object responseObject,
          Object requestBody) throws HttpClientException {
    requestForUpdate(target, "POST", responseObject, requestBody);
  }

  /**
   * Perform an HTTP DELETE request for the given resource.
   * 
   * @param target the URL to request (relative URLs will resolve
   *          against the {@link #getBaseUrl() base URL}).
   * @throws HttpClientException if an exception occurs during
   *           processing, or the server returns a 4xx or 5xx error
   *           response (in which case the response JSON message will be
   *           available as a {@link JsonNode} in the exception).
   */
  public void delete(String target) throws HttpClientException {
    request(target, "DELETE", new TypeReference<JsonNode>() {
    }, null);
  }

  /**
   * Perform an HTTP GET request on a URL whose response is expected to
   * be a 3xx redirection, and return the target redirection URL.
   * 
   * @param source the URL to request (relative URLs will resolve
   *          against the {@link #getBaseUrl() base URL}).
   * @return the URL returned by the "Location" header of the
   *         redirection response.
   * @throws HttpClientException if an exception occurs during
   *           processing, or the server returns a 4xx or 5xx error
   *           response (in which case the response JSON message will be
   *           available as a {@link JsonNode} in the exception), or if
   *           the response was not a 3xx redirection.
   */
  public URL getRedirect(URL source) throws HttpClientException {
    try {
      HttpURLConnection connection = (HttpURLConnection)source.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Authorization", authorizationHeader);
      connection.setRequestProperty("Accept", "application/json");
      connection.setInstanceFollowRedirects(false);
      int responseCode = connection.getResponseCode();
      // make sure we read any response content
      readResponseOrError(connection, new TypeReference<JsonNode>() {
      }, false);
      if(responseCode >= 300 && responseCode < 400) {
        // it was a redirect
        String redirectUrl = connection.getHeaderField("Location");
        return new URL(redirectUrl);
      } else {
        throw new HttpClientException("Expected redirect but got "
                + responseCode);
      }
    } catch(IOException e) {
      throw new HttpClientException(e);
    }
  }
}
