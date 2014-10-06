/*
 * Copyright (c) 2014
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
package tableCreator;


import java.util.HashMap;
import java.util.Map;
import com.google.common.base.Strings;

/**
 * Creates a table. 
 * @author YavorPetkov
 *
 */
public class ConsoleStringTable {

    private class Index {

        int row, colum;

        public Index (int r, int c) {
            row= r;
            colum= c;
        }

        @Override
        public boolean equals (Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Index other= (Index) obj;
            if (colum != other.colum)
                return false;
            if (row != other.row)
                return false;
            return true;
        }

        @Override
        public int hashCode () {
            final int prime= 31;
            int result= 1;
            result= prime * result + colum;
            result= prime * result + row;
            return result;
        }
    }

    Map<Index, String> strings= new HashMap<ConsoleStringTable.Index, String>();
    Map<Integer, Integer> columSizes= new HashMap<Integer, Integer>();

    int numRows= 0;
    int numColumns= 0;

    public void addString (int row, int colum, String content) {
        numRows= Math.max(numRows, row + 1);
        numColumns= Math.max(numColumns, colum + 1);

        Index index= new Index(row, colum);
        strings.put(index, colum==0?""+content:"|"+content);

        setMaxColumnSize(colum, colum==0?""+content:"|"+content);
    }

    private void setMaxColumnSize (int colum, String content) {
        int size= content.length();
        Integer currentSize= columSizes.get(colum);
        if (currentSize == null || (currentSize != null && currentSize < size)) {
            columSizes.put(colum, size);
        }
    }

    public int getColumSize (int colum) {
        Integer size= columSizes.get(colum);
        if (size == null) {
            return 0;
        } else {
            return size;
        }
    }
    
    public int getTableSize (){
    	int sum=0;
    	for(int i=0;i<columSizes.size();i++){
    		sum+=getColumSize(i);
    	}
    	return sum;
    }

    public String getString (int row, int colum) {
        Index index= new Index(row, colum);
        String string= strings.get(index);
        if (string == null) {
            return "";
        } else {
            return string;
        }
    }

    public String getTableAsString (int padding) {
        String out= "";
        for (int r= 0; r < numRows; r++) {
            for (int c= 0; c < numColumns; c++) {
                int columSize= getColumSize(c);
                String content= getString(r, c);
                int pad= c == numColumns - 1 ? 0 : padding;
                out+= Strings.padEnd(content, columSize + pad, ' ');
            }
           
            if (r < numRows - 1) {
                out+= "\n";
            }
            if(r == 0){
            	out+=Strings.padEnd("", getTableSize(), '-');
            	out+= "\n";
            }
        }
        return out;
    }

    @Override
    public String toString () {
        return getTableAsString(1);
    }

}