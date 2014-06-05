package model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

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
	private String mimeType;
	private String[] annotationSelectors;
	
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
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}	
	public String[] getAnnotationSelectors() {
		return annotationSelectors;
	}
	public void setAnnotationSelectors(String[] annotationSelectors) {
		this.annotationSelectors = annotationSelectors;
	}
}
