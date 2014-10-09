/*
 * S4 Java client library
 * Copyright (c) 2014, Ontotext AD, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
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
