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
package com.ontotext.s4.service;

/**
 * Enumeration of the MIME types supported by the online API.
 * 
 */
public enum SupportedMimeType {

  PLAINTEXT("text/plain"),
  HTML("text/html"),
  XML_APPLICATION("application/xml"),
  XML_TEXT("text/xml"),
  PUBMED("text/x-pubmed"),
  COCHRANE("text/x-cochrane"),
  MEDIAWIKI("text/x-mediawiki"),
  TWITTER_JSON("text/x-json-twitter");

  private SupportedMimeType(String type) {
    this.value = type;
  }

  public final String value;
}
