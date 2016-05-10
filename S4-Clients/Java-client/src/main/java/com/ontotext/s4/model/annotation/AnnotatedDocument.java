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
package com.ontotext.s4.model.annotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.ontotext.s4.common.ServiceResponseEntity;
import com.ontotext.s4.model.image.ClassifiedImage;

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
    private String text;

    /**
     * Annotations grouped by type. The keys in this map are annotation
     * types and the corresponding values are the (possibly empty) list of
     * annotations of that type that were found by the pipeline.
     */
    private Map<String, List<Annotation>> entities;

    /**
     * List of image annotations (if explicitly specified)
     */
    private List<ClassifiedImage> images;

    /**
     * Holder for any additional properties found in the JSON response
     * apart from the text and entities. Typically this will only contain
     * values if the original document that was processed was Twitter
     * JSON.
     */
    private Map<String, JsonNode> otherFeatures;

    public AnnotatedDocument() {

    }

    public AnnotatedDocument(String text, Map<String, List<Annotation>> entities, List<ClassifiedImage> images, Map<String, JsonNode> otherFeatures) {
        this.text = text;
        this.entities = entities;
        this.images = images;
        this.otherFeatures = otherFeatures;
    }


    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public Map<String, List<Annotation>> getEntities() {
        return this.entities;
    }
    public void setEntities(Map<String, List<Annotation>> entities) {
        this.entities = entities;
    }

    public Map<String, JsonNode> getOtherFeatures() {
        return this.otherFeatures;
    }
    public void setOtherFeatures(Map<String, JsonNode> otherFeatures) {
        this.otherFeatures = otherFeatures;
    }

    public List<ClassifiedImage> getImages() {
        return this.images;
    }
    public void setImages(List<ClassifiedImage> images) {
        this.images = images;
    }

    /**
     * Catch-all setter used by Jackson to deserialize unknown properties
     * into the {@link #otherFeatures} map.
     *
     * @param name The name of the feature
     * @param value The value of the feature
     */
    @JsonAnySetter
    public void addFeature(String name, JsonNode value) {
        if(otherFeatures == null) {
        	otherFeatures = new HashMap<>();
        }
        otherFeatures.put(name, value);
    }
}
