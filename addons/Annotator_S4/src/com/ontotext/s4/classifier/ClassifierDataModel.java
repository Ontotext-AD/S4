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
