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

package com.ontotext.s4.model.classification;

import com.ontotext.s4.common.ServiceResponseEntity;

import java.util.List;

/**
 * Object-oriented representation of the classification information for a document, as returned
 * by the S4 API. Document classification information consists of
 *
 * <ul>
 * <li>The category with the highest confidence score</li>
 * <li>List of the top 3 categories ranked by confidence</li>
 * </ul>
 */
public class ClassifiedDocument extends ServiceResponseEntity {

    /**
     * The most probable category (as specified on docs.s4.ontotext.com)
     * For more information, please visit
     * http://docs.s4.ontotext.com/display/S4docs/News+Classifier#NewsClassifier-DescriptionofCategories
     */
    private String category;

    /**
     * List of the 3 most probable categories to which the document belongs, as well as their probability score
     * Note: This includes the {@link #category } property which will always have the highest score
     */
    private List<ClassificationCategory> allScores;

    public ClassifiedDocument() {

    }

    public ClassifiedDocument(String category, List<ClassificationCategory> allScores) {
        this.category = category;
        this.allScores = allScores;
    }
    
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public List<ClassificationCategory> getAllScores() {
        return this.allScores;
    }
    public void setAllScores(List<ClassificationCategory> allScores) {
        this.allScores = allScores;
    }
}
