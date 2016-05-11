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
    public class ClassifiedImage
    {

        private String Tagging_Id;

        private String Image;

        private List<ImageTag> Tags;

        private List<ImageCategory> Categories;

        /// <summary>
        /// Tag ID of the image
        /// </summary>
        public String tagging_id
        {
            get { return Tagging_Id; }
            set { Tagging_Id = value; }
        }

        /// <summary>
        /// URL address of the image
        /// </summary>
        public String image
        {
            get { return Image; }
            set { Image = value; }
        }

        /// <summary>
        /// List of specific image tags
        /// </summary>
        public List<ImageTag> tags
        {
            get { return Tags; }
            set { Tags = value; }
        }

        /// <summary>
        /// List of specific image categories
        /// </summary>
        public List<ImageCategory> categories
        {
            get { return Categories; }
            set { Categories = value; }
        }

        public ClassifiedImage()
        {

        }

        public ClassifiedImage(String tagging_id, String image, List<ImageTag> tags, List<ImageCategory> categories)
        {
            this.Tagging_Id = tagging_id;
            this.Image = image;
            this.Tags = tags;
            this.Categories = categories;
        }
    }
}
