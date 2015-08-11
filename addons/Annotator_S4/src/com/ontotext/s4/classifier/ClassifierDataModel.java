/* Self-Service Semantic Suite
Copyright (c) 2014, Ontotext AD, All rights reserved.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3.0 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library.
*/

package com.ontotext.s4.classifier;

import java.util.HashMap;
import java.util.Map;

public class ClassifierDataModel {

	private String category;
	private Score[] allScores;
	
	public ClassifierDataModel() {
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Score[] getAllScores() {
		return allScores;
	}

	public void setAllScores(Score[] allScores) {
		this.allScores = allScores;
	}
	
	public Map<String,Float> getScoreAsMap(){
		Map<String,Float> mapToReturn=new HashMap<String,Float>();
		for(Score score:getAllScores()){
			mapToReturn.put(score.getLabel(), score.getScore());
		}
		return mapToReturn;
		
	}
}
