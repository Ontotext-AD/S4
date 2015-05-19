/*
 * Copyright (c) 2015
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
package com.ontotext.s4.gdbaas.createRepo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * @author YavorPetkov
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRepositoryRequest {
	
	@JsonProperty("repositoryID")
	private String repoId;
	@JsonProperty("title")
	private String title;
	@JsonProperty("ruleset")
	private String ruleset;
	@JsonProperty("base-URL")
	private String baseURL;
	@JsonProperty("enablePredicateList")
	private boolean predicateIndices;
	@JsonProperty("proportions")
	private List<RepoProportion> repositoryList;
	
	
	public String getRepoName() {
		return repoId;
	}
	public void setRepoName(String repoName) {
		this.repoId = repoName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRuleset() {
		return ruleset;
	}
	public void setRuleset(String rulset) {
		this.ruleset = rulset;
	}
	public String getBaseURL() {
		return baseURL;
	}
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	public boolean isPredicateIndices() {
		return predicateIndices;
	}
	public void setPredicateIndices(boolean predicateIndices) {
		this.predicateIndices = predicateIndices;
	}
	
	public static class RepoProportion {
		public String repositoryID;
		public int percentage;
		
		public String toString() {
			return repositoryID + ':' + percentage;
		}
	}

}
