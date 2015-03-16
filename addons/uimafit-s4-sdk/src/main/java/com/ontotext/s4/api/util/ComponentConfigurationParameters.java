/*
* Copyright (c) 2015
*
* This file is part of the s4.ontotext.com REST client library, and is
* licensed under the Apache License, Version 2.0 (the "License");
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

package com.ontotext.s4.api.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Allows defining component parameters as key-value pairs using chained withConfigParameter(...) method calls
 * and finally transforming those pairs to an object array that is accepted by the uimaFIT framework.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-02-23
 */
public class ComponentConfigurationParameters {

    private ComponentConfigurationParameters() {
        super();
    }

    public static ComponentConfigurationParameters newInstance() {
        return new ComponentConfigurationParameters();
    }

    private final List<AbstractMap.SimpleEntry<String, Object>> parameters = new ArrayList<>();

    /**
     * Add a single parameter to the list.
     *
     * @param parameterName
     *            the key for the parameter(it's name)
     * @param parameterValue
     *            the value for the parameter
     * @return this for chaining
     */
    public ComponentConfigurationParameters withConfigParameter(String parameterName, Object parameterValue) {
        parameters.add(new AbstractMap.SimpleEntry<>(parameterName, parameterValue));
        return this;
    }

    /**
     * Method for ordering uimaFIT component parameters as an object array which is required by the framework.
     *
     * @return object array with parameter keys and parameter values ordered one after the other
     */
    public Object[] getParametersArray() {
        Object[] arr = new Object[parameters.size() * 2];
        int idx = 0;
        for (Map.Entry<String, Object> pair : parameters) {
            String key = pair.getKey();
            String value = (String) pair.getValue();
            arr[idx] = key;
            arr[++idx] = value;
            idx++;
        }

        return arr;
    }

}
