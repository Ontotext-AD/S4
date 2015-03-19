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

package com.ontotext.s4.api.restclient;

/**
 * An enum that represents the S4 Rest Service Endpoints.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * @since 2015-02-19.
 */
public enum S4Endpoints {

    TWITIE("https://text.s4.ontotext.com/v1/twitie"),
    NEWS("https://text.s4.ontotext.com/v1/news"),
    SBT("https://text.s4.ontotext.com/v1/sbt");

    private String value;

    private S4Endpoints(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
