package com.ontotext.s4.catalog;

public class ServicesCatalog {
	
	public static ServiceDescriptor getItem(String itemName){
		ServiceDescriptor item=new ServiceDescriptor();
		switch(itemName){
		case "TwitIE":{
			item.name="TwitIE";
			item.onlineUrl="https://text.s4.ontotext.com/v1/twitie";
			return item;
		}
		case "SBT":{
			item.name="SBT";
			item.onlineUrl="https://text.s4.ontotext.com/v1/sbt";
			return item;
		}
		case "news":{
			item.name="news";
			item.onlineUrl="https://text.s4.ontotext.com/v1/news";
			return item;
		}
		default:{
			throw new UnsupportedOperationException();
		}
		}

	}
}
