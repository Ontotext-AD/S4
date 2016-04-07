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
package com.ontotext.s4.common;

import com.ontotext.s4.client.HttpClient;

/**
 * Common superclass for all objects that represent API responses that
 * will be parsed from JSON. The Jackson ObjectMapper used by
 * the {@link HttpClient} is set up to automatically inject the client
 * instance into deserialized objects, allowing them to have their own
 * methods that make further requests to the API.
 * 
 */
public class ServiceResponseEntity {

}
