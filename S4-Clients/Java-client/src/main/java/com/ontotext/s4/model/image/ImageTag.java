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
package com.ontotext.s4.model.image;


public class ImageTag {

    /**
     * Name of the tag
     */
    private String tag;

    /**
     * Confidence score of the tag
     */
    private double confidence;


    public ImageTag() {

    }

    public ImageTag(String tag, double confidence) {
        this.tag = tag;
        this.confidence = confidence;
    }

    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public double getConfidence() {
        return this.confidence;
    }
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
