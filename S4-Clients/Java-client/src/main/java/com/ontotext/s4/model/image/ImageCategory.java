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

/**
 *
 */
public class ImageCategory {

    /**
     * Name of the category
     */
    private String name;

    /**
     * Confidence score of the category (0-100)
     */
    private double confidence;


    public ImageCategory() {

    }

    public ImageCategory(String name, double confidence) {
        this.name = name;
        this.confidence = confidence;
    }


    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getConfidence() {
        return this.confidence;
    }
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
