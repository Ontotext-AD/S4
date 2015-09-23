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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.ontotext.s4.common.ServiceResponseEntity;

/**
 * Object-oriented representation of an annotated document as returned
 * by the S4 online API. An annotated document consists of
 * 
 * <ul>
 * <li>The plain text extracted from the source document</li>
 * <li>Sets of annotations, grouped by type. Each annotation marks a
 * particular span of text, as indiated by its start and end offsets,
 * which are zero-based indexes into the plain text.</li>
 * <li>possibly other features, if the original document was JSON</li>
 * </ul>
 * 
 */
public class AnnotatedDocument extends ServiceResponseEntity {
  /**
   * The <em>plain text</em> of the document, extracted from the
   * original source. Any markup that was part of the original document
   * (e.g. XML or HTML) will have been removed, leaving just the plain
   * text.
   */
  public String text;

  /**
   * Annotations grouped by type. The keys in this map are annotation
   * types and the corresponding values are the (possibly empty) list of
   * annotations of that type that were found by the pipeline.
   */
  public Map<String, List<Annotation>> entities;

  /**
   * Holder for any additional properties found in the JSON response
   * apart from the text and entities. Typically this will only contain
   * values if the original document that was processed was Twitter
   * JSON.
   */
  public Map<String, JsonNode> otherFeatures;

  /**
   * Catch-all setter used by Jackson to deserialize unknown properties
   * into the {@link #otherFeatures} map.
   */
  @JsonAnySetter
  public void addFeature(String name, JsonNode value) {
    if(otherFeatures == null) otherFeatures = new HashMap<String, JsonNode>();
    otherFeatures.put(name, value);
  }
}
