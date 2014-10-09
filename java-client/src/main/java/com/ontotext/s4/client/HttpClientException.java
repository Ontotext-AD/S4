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

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Exception representing any problem communicating with the S4
 * REST API. In cases where the server could successfully be contacted
 * but it returned an error response (4xx or 5xx) with a JSON response
 * body, that response will be available as a {@link JsonNode} from the
 * {@link #getResponse()} method.
 * 
 */
public class HttpClientException extends RuntimeException {

  private static final long serialVersionUID = -7227598956545774156L;

  /**
   * The JSON error response message, if applicable.
   */
  private JsonNode response;

  public HttpClientException() {
  }

  public HttpClientException(String message) {
    super(message);
  }

  public HttpClientException(Throwable cause) {
    super(cause);
  }

  public HttpClientException(String message, Throwable cause) {
    super(message, cause);
  }

  public HttpClientException(String message, JsonNode response) {
    super(message);
    this.response = response;
  }

  /**
   * If this exception resulted from a 4xx or 5xx error response from
   * the server, this method provides access to the response body.
   * 
   * @return the error response body as a Jackson {@link JsonNode}, or
   *         <code>null</code> if no response is available (e.g. if this
   *         exception wraps an IOException rather than representing an
   *         error response from the server).
   */
  public JsonNode getResponse() {
    return response;
  }

}
