package com.ontotext.s4.api;

/**
 * A class that represents the S4 Rest Service Endpoints.
 * @author YavorPetkov
 *
 */
public enum S4Endpoints {
	TWITIE("https://text.s4.ontotext.com/v1/twitie"), 
	NEWS("https://text.s4.ontotext.com/v1/news"), 
	SBT("https://text.s4.ontotext.com/v1/sbt");

	private String value;

	private S4Endpoints(String value) {
		this.value = value;
	}

	public String toString() {
		return this.value;
	}
}
