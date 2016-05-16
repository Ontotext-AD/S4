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

package table;
import java.io.IOException;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Loads JSON string into the memory. Then creates table like this:
 * <p>
 * header1 |header2<br>
 * ----------------<br>
 * param1  |param2<br>
 * param3  |param4<br>
 * 
 * @author YavorPetkov
 *
 */
public class TableCreator {

	public static final String createTableFromJSON(String json) {
		
		//loads json into memmory
		JsonNode jsonObject = loadJsonIntoMemory(json);
		
		//get table headers
		String[] tableHeaders = getTableHeaders(jsonObject);
		ConsoleStringTable table = new ConsoleStringTable();
		for (int i = 0; i < tableHeaders.length; i++) {
			table.addString(0, i, tableHeaders[i]);
		}
		Iterator<JsonNode> iterator = jsonObject.get("results").get("bindings")
				.getElements();
		int i = 1;
		while (iterator.hasNext()) {
			JsonNode node = iterator.next();
			for (int j = 0; j < tableHeaders.length; j++) {
				table.addString(i, j, node.get(tableHeaders[j]).get("value")
						.toString());
			}
			i++;
		}
		return table.toString();
	}

	/**
	 * 
	 * @param jsonString JSON response from LOD service
	 * @return In-memory tree
	 */
	private static JsonNode loadJsonIntoMemory(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readTree(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param jsonObject In-memory tree
	 * @return Array of the headers
	 */
	private static String[] getTableHeaders(JsonNode jsonObject){
		String[] tableHeaders = jsonObject.get("head").get("vars").toString()
				.split(",");
		for (int i = 0; i < tableHeaders.length; i++) {
			tableHeaders[i] = tableHeaders[i].replaceAll("[^\\w+]", "");
		}
		return tableHeaders!=null?tableHeaders:new String[0];
	}
	
	

}
