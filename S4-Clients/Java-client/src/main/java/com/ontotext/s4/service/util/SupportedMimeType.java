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
package com.ontotext.s4.service.util;

/**
 * Enumeration of the MIME types supported by the online API.
 */
public enum SupportedMimeType {

    PLAINTEXT("text/plain"),
    HTML("text/html"),
    XML_APPLICATION("application/xml"),
    XML_TEXT("text/xml"),
    PUBMED("text/x-pubmed"),
    COCHRANE("text/x-cochrane"),
    MEDIAWIKI("text/x-mediawiki"),
    TWITTER_JSON("text/x-json-twitter");

    private SupportedMimeType(String type) {
        this.value = type;
    }

    public final String value;
}
