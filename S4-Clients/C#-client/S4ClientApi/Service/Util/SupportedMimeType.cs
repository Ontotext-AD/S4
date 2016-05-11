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
using Ontotext.S4.common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ontotext.S4.service
{
    /// <summary>
    /// Enumeration of the MIME types supported by the online API.
    /// </summary>
    public enum SupportedMimeType
    {
        /// <summary>
        /// text/plain
        /// </summary>
        [StringValueAtribute("text/plain")]
        PLAINTEXT = 1,
        /// <summary>
        /// text/html
        /// </summary>
        [StringValueAtribute("text/html")]
        HTML = 2,
        /// <summary>
        /// application/xml
        /// </summary>
        [StringValueAtribute("application/xml")]
        XML_APPLICATION = 3,
        /// <summary>
        /// text/xml
        /// </summary>
        [StringValueAtribute("text/xml")]
        XML_TEXT = 4,
        /// <summary>
        /// text/x-pubmed
        /// </summary>
        [StringValueAtribute("text/x-pubmed")]
        PUBMED = 5,
        /// <summary>
        /// text/x-cochrane
        /// </summary>
        [StringValueAtribute("text/x-cochrane")]
        COCHRANE = 6,
        /// <summary>
        /// text/x-mediawiki
        /// </summary>
        [StringValueAtribute("text/x-mediawiki")]
        MEDIAWIKI = 7,
        /// <summary>
        /// text/x-json-twitter
        /// </summary>
        [StringValueAtribute("text/x-json-twitter")]
        TWITTER_JSON = 8


    }

    /// <summary>
    /// 
    /// </summary>
    public static class StringValueExtension
    {
        /// <summary>
        /// 
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static string ToStringValue(this Enum value)
        {
            StringValueAtribute[] attributes = (StringValueAtribute[])value.GetType().GetField(value.ToString()).GetCustomAttributes(typeof(StringValueAtribute), false);
            return ((attributes != null) && (attributes.Length > 0)) ? attributes[0].StringValue : value.ToString();
        }

        /// <summary>
        /// 
        /// </summary>
        /// <typeparam name="T"></typeparam>
        /// <param name="str"></param>
        /// <returns></returns>
        public static T ToEnum<T>(this string str)
        {
            foreach (T item in Enum.GetValues(typeof(T)))
            {
                StringValueAtribute[] attributes = (StringValueAtribute[])item.GetType().GetField(item.ToString()).GetCustomAttributes(typeof(StringValueAtribute), false);
                if ((attributes != null) && (attributes.Length > 0) && (attributes[0].StringValue.Equals(str)))
                {
                    return item;
                }
            }
            return (T)Enum.Parse(typeof(T), str, true);
        }
    }
}
