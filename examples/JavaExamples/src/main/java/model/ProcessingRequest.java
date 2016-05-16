/*
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

package model;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * A bean class which wraps the parameters sent to the Self Service Semantic Suite API endpoint
 * Supports serialization into JSON
 * @author petar.kostov
 *
 */
public class ProcessingRequest {

	private static ObjectMapper mapper = new ObjectMapper();
	
	private String document;
	private String documentUrl;
	private String documentType;
	private Boolean imageTagging;
	private Boolean imageCategorization;
	
	public String toJSON() throws JsonGenerationException, JsonMappingException, IOException {
		return mapper.writeValueAsString(this);
	}
	
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getDocumentUrl() {
		return documentUrl;
	}
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String mimeType) {
		this.documentType = mimeType;
	}	

    public Boolean getImageTagging() {
        return imageTagging;
    }

    public void setImageTagging(Boolean imageTagging) {
        this.imageTagging = imageTagging;
    }

    public Boolean getImageCategorization() {
        return imageCategorization;
    }

    public void setImageCategorization(Boolean imageCategorization) {
        this.imageCategorization = imageCategorization;
    }
}
