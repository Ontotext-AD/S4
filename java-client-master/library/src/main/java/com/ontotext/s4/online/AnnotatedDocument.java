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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.ontotext.s4.common.ApiObject;

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
public class AnnotatedDocument extends ApiObject {
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
