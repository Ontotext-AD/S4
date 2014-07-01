/*
 * Copyright (c) 2014 Ontotext AD
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
package com.ontotext.s4.online;

/**
 * Enumeration of the valid response types accepted by the online API.
 * 
 */
public enum ResponseFormat {
  JSON("application/gate+json"),

  GATE_XML("application/gate+xml");

  /**
   * The HTTP "Accept" header corresponding to this response format.
   */
  public final String acceptHeader;

  private ResponseFormat(String acceptHeader) {
    this.acceptHeader = acceptHeader;
  }

}
