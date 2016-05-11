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

namespace S4ClientApi.Service.Images
{
    public class ImageCategory
    {
        private String Name;
        private double Confidence;

        /// <summary>
        /// Name of the category
        /// </summary>
        public String name
        {
            get { return Name; }
            set { Name = value; }
        }

        /// <summary>
        /// Confidence Score of the category (0-100)
        /// </summary>
        public double confidence
        {
            get { return Confidence; }
            set { Confidence = value; }
        }

        public ImageCategory()
        {

        }

        public ImageCategory(String name, double confidence)
        {
            this.Name = name;
            this.Confidence = confidence;
        }
    }
}
