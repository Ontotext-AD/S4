/*
 * S4 C# client library
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
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Ontotext.S4.common;

namespace Ontotext.S4.service
{
    /// <summary>
    /// Response Format
    /// </summary>
    public enum ResponseFormat
    {
        /// <summary>
        /// application/json
        /// </summary>
        [StringValueAtribute("application/json")]
        JSON = 1,
        /// <summary>
        /// application/gate+json
        /// </summary>
        [StringValueAtribute("application/gate+json")]
        GATE_JSON = 2,
        /// <summary>
        /// application/gate+xml
        /// </summary>
        [StringValueAtribute("application/gate+xml")]
        GATE_XML = 3
    }
}
