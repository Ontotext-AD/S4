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

namespace S4ClientApi.Service
{
    public class ClassificationCategory
    {
        private String Label;
        private double Score;

        public ClassificationCategory()
        {

        }

        public ClassificationCategory(String category, double confidence)
        {
            this.Label = category;
            this.Score = confidence;
        }

        /// <summary>
        /// The type label of the specified category
        /// </summary>
        public String label
        {
            get { return Label; }
            set { Label = value; }
        }

        /// <summary>
        /// The confidence score of the specified category
        /// </summary>
        public double score
        {
            get { return Score; }
            set { Score = value; }
        }
    }
}
