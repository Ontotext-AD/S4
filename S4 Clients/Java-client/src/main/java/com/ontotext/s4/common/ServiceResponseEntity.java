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
package com.ontotext.s4.common;

import com.ontotext.s4.client.HttpClient;
import com.fasterxml.jackson.annotation.JacksonInject;

/**
 * Common superclass for all objects that represent API responses that
 * will be parsed from JSON. The Jackson {@link ObjectMapper} used by
 * the {@link HttpClient} is set up to automatically inject the client
 * instance into deserialized objects, allowing them to have their own
 * methods that make further requests to the API.
 * 
 */
public class ServiceResponseEntity {
  protected transient HttpClient client;

  /**
   * Called by Jackson at parse time - you should only need to call this
   * method explicitly if you construct an API object manually rather
   * than by parsing JSON.
   */
  @JacksonInject
  public void setClient(HttpClient client) {
    this.client = client;
  }

}
