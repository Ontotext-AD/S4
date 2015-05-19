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
package com.ontotext.s4.gdbaas.createRepo;

import com.ontotext.s4.gdbaas.createRepo.model.CreateRepositoryRequest;
import com.ontotext.s4.gdbaas.createRepo.service.GdbaasClient;

/**
 * 
 * @author YavorPetkov
 *
 */
public class Main {
	
	public static void main(String[] args) throws Exception{
		
		String userId="<userId>";
		String dbaasName="<database name>";
		String repositoryId="<repository name>";
		String baseURI="http://www.example.org";
		String ApiKey = "<api key>";
		String ApiPass = "<api pass>";
		String pathToTheFile="< path to the rdf file>";
		String ruleset="owl-horst-optimized";
		
		GdbaasClient gdbaas=new GdbaasClient(userId,dbaasName, ApiKey, ApiPass);
		CreateRepositoryRequest createRepo=new CreateRepositoryRequest();
		createRepo.setRepoName(repositoryId);
		createRepo.setRuleset(ruleset);
		
		gdbaas.createRepository(createRepo);
		
		gdbaas.checkRepository(repositoryId);
		
		//gdbaas.uploadFile(repositoryId,baseURI,pathToTheFile);
			
		//gdbaas.checkRepository(repositoryId);
		
		//gdbaas.deleteRepository(repositoryId);
	}
}
