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
package com.ontotext.s4.service;

import com.ontotext.s4.catalog.ServicesCatalog;
import com.ontotext.s4.service.impl.S4AnnotationClientImpl;
import com.ontotext.s4.service.impl.S4ClassificationClientImpl;


public class ServiceClientsFactory {
    /**
     * Constructs a simple {@link S4AnnotationClient} to use with the 'news', 'sbt' or 'twitie' services
     *
     * @param service The service that is going to be used
     * @param apiKey Your S4 Api Key
     * @param keySecret Your S4 Key Secret
     * @return An {@link S4AnnotationClient} to access the S4 'news', 'sbt' or 'twitie' services
     */
    public static S4AnnotationClient createAnnotationClient (String service, String apiKey, String keySecret) {
        return new S4AnnotationClientImpl(ServicesCatalog.getItem(service), apiKey, keySecret);
    }

    /**
     * Constructs a simple {@link S4ClassificationClient} to use with the 'news-classifier' service
     *
     * @param apiKey Your S4 Api Key
     * @param keySecret Your S4 Key Secret
     * @return An {@link S4ClassificationClient} to access the S4 'news-classifier' service
     */
    public static S4ClassificationClient createClassificationClient(String apiKey, String keySecret) {
        return new S4ClassificationClientImpl(ServicesCatalog.getItem("news-classifier"), apiKey, keySecret);
    }
}