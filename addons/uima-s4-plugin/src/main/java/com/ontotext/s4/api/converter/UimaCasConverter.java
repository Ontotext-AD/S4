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

package com.ontotext.s4.api.converter;

import org.apache.uima.cas.CAS;

/**
 * An interface representing operations needed to transform a custom annotated document format into a UIMA CAS document.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov@ontotext.com>
 * <p/>
 * Date added: 2015-02-19
 */
public interface UimaCasConverter {

    /**
     * Operation responsible for auto-discovering a UIMA type system from the original document's annotations.
     *
     * @param originalTypes original annotation types/names
     */
    void inferCasTypeSystem(Iterable<String> originalTypes);

    /**
     * Operation responsible for converting the custom annotations into native UIMA datastructures. .
     *
     * @param cas the CAS object to be populated
     */
    void convertAnnotations(CAS cas);

    /**
     * Operation responsible for setting the document's raw text to the CAS objects.
     *
     * @param cas the CAS object to be populated
     */
    void setSourceDocumentText(CAS cas);
}
