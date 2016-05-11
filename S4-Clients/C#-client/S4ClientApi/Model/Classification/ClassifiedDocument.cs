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
using S4ClientApi.Service;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ontotext.S4.ontotext.service
{
    /// <summary>
    ///  Object-oriented representation of the classification information for a document as returned by the S4 online API
    /// </summary>
    public class ClassifiedDocument
    {
        private String Category;
        private List<ClassificationCategory> AllScores;

        public ClassifiedDocument()
        {

        }

        public ClassifiedDocument(String category, List<ClassificationCategory> allScores)
        {
            this.Category = category;
            this.AllScores = allScores;
        }

        /// <summary>
        /// The most probable category (as specified on docs.s4.ontotext.com)
        /// For more information, please visit
        /// http://docs.s4.ontotext.com/display/S4docs/News+Classifier#NewsClassifier-DescriptionofCategories
        /// </summary>
        public String category
        {
            get { return Category; }
            set { Category = value; }
        }

        /// <summary>
        /// List of the 3 most probable categories to which the document belongs, as well as their probability score
        /// Note: This includes the Category property which will always have the highest score
        /// </summary>
        public List<ClassificationCategory> allScores
        {
            get { return AllScores; }
            set { AllScores = value; }
        }
    }
}
