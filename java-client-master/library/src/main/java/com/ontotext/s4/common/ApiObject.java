/*
 * Copyright (c) 2014
 *
 * This file is part of the s4.ontotext.com REST client library, and is
 * licensed under the Apache License, Version 2.0 (the "License");
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
package com.ontotext.s4.common;

import com.ontotext.s4.client.RestClient;
import com.fasterxml.jackson.annotation.JacksonInject;

/**
 * Common superclass for all objects that represent API responses that
 * will be parsed from JSON. The Jackson {@link ObjectMapper} used by
 * the {@link RestClient} is set up to automatically inject the client
 * instance into deserialized objects, allowing them to have their own
 * methods that make further requests to the API.
 * 
 */
public class ApiObject {
  protected transient RestClient client;

  /**
   * Called by Jackson at parse time - you should only need to call this
   * method explicitly if you construct an API object manually rather
   * than by parsing JSON.
   */
  @JacksonInject
  public void setClient(RestClient client) {
    this.client = client;
  }

}
