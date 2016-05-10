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
package com.ontotext.s4.catalog;

public class ServicesCatalog {
	
	public static final String S4_SERVICE_ENDPOINT_URL = "https://text.s4.ontotext.com/v1/";
	
	public static ServiceDescriptor getItem(String itemName) {

        if (itemName == null) {
        	throw new IllegalArgumentException("No service id provided ( expected one of: 'twitie', 'news', 'sbt' or 'news-classifier' )");
        }
        ServiceDescriptor item = new ServiceDescriptor();
        switch(itemName.toLowerCase()){
            case "twitie":{
                item.setName("TwitIE");
                item.setServiceUrl(S4_SERVICE_ENDPOINT_URL + "twitie");
                item.setDescription("The Twitter analytics service of S4 is based on the TwitIE open source "
                        + "information extraction pipeline by the GATE platform.");
                return item;
			}
            case "sbt":{
                item.setName("SBT");
                item.setServiceUrl(S4_SERVICE_ENDPOINT_URL + "sbt");
                item.setDescription("The Semantic Biomedical Tagger (SBT) has a built-in capability to recognize "
                        + "133 biomedical entity types and semantically link them to the knowledge base systems");
                return item;
			}
            case "news":{
                item.setName("news");
                item.setServiceUrl(S4_SERVICE_ENDPOINT_URL + "news");
                item.setDescription("The News Annotation service retrieves various types of entities from texts "
                        + "as well as the relations between them.");
                return item;
            }
            case "news-classifier":{
                item.setName("news-classifier");
                item.setServiceUrl(S4_SERVICE_ENDPOINT_URL + "news-classifier");
                item.setDescription("The News classifier service assigns each input document a category, "
                        + "based on the 17 top-level categories of IPTC Subject Reference System");
                return item;
            }
            default:{
                throw new UnsupportedOperationException("Unsupported service id (" + itemName + "). For custom service endpoints, please use ServiceDescriptor class directly");
            }
        }
    }
}
