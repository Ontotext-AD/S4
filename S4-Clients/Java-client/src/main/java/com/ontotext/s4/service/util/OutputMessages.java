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
 * Enumeration of the messages the S4 CommandLine Tool outputs
 */
public enum OutputMessages {
    HELP("This is the S4 CLI help screen"),

    INFO("For more information, run the program with the '-h' flag"),

    CLASSIFIER_USAGE("The '--img-tagging' and '--img-categorization' parameters are applicable only for the following services: 'twitie', 'news', 'sbt'."),

    URL_NOT_FOUND("In order to use image tagging and/or categorization, you must pass a URL location ('-u', '--url-location')."),

    NO_INPUTS_PASSED("You must either use a URL location ('-u', '--url-location') or a path to a file in your local folders ('-f', '--file-path') but not none."),

    BOTH_INPUTS_PASSED("You must either use a URL location ('-u', '--url-location') or a path to a file in your local folders ('-f', '--file-path') but not both."),

    UNSUPPORTED_MIME("Unsupported MIME type. Available options are: PLAINTEXT, HTML, XML_APPLICATION, XML_TEXT, PUBMED, COCHRANE, MEDIAWIKI, TWITTER_JSON."),

    UNSUPPORTED_SERVICE("Unsupported service. Available S4 services are: 'news', 'sbt', 'twitie', 'news-classifier'.");


    public final String message;

    private OutputMessages(String message) {
        this.message = message;
    }
}
