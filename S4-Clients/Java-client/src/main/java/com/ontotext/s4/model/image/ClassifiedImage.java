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


import java.util.List;


public class ClassifiedImage {

    /**
     * Tag ID of the image
     */
    private String tagging_id;

    /**
     * URL address of the image
     */
    private String image;

    /**
     * List of specific image tags
     */
    private List<ImageTag> tags;

    /**
     * List of specific image categories
     */
    private List<ImageCategory> categories;

    public ClassifiedImage() {

    }

    public ClassifiedImage(String tagging_id, String image, List<ImageTag> tags, List<ImageCategory> categories) {
        this.tagging_id = tagging_id;
        this.image = image;
        this.tags = tags;
        this.categories = categories;
    }

    public String getTaggingID() {
        return this.tagging_id;
    }
    public void setTaggingID(String taggingID) {
        this.tagging_id = taggingID;
    }

    public String getImage() {
        return this.image;
    }
    public void setImage(String imageURL) {
        this.image = imageURL;
    }

    public List<ImageTag> getTags() {
        return this.tags;
    }
    public void setTags(List<ImageTag> tags) {
        this.tags = tags;
    }

    public List<ImageCategory> getCategories() {
        return this.categories;
    }
    public void setCategories(List<ImageCategory> categories) {
        this.categories = categories;
    }
}
