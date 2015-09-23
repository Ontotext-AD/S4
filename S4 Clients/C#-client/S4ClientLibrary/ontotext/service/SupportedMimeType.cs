/*
 * S4 C# client library
 * Copyright (c) 2014, Ontotext AD, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
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
    /// 
    /// </summary>
    public enum SupportedMimeType
    {
        [StringValueAtribute("text/plain")]
        PLAINTEXT=1,
        [StringValueAtribute("text/html")]
        HTML=2,
        [StringValueAtribute("application/xml")]
        XML_APPLICATION=3,
        [StringValueAtribute("text/xml")]
        XML_TEXT=4,
        [StringValueAtribute("text/x-pubmed")]
        PUBMED=5,
        [StringValueAtribute("text/x-cochrane")]
        COCHRANE=6,
        [StringValueAtribute("text/x-mediawiki")]
        MEDIAWIKI=7,
        [StringValueAtribute("text/x-json-twitter")]
        TWITTER_JSON = 8


    }

    /// <summary>
    /// 
    /// </summary>
    public static class StringValueExtension
    {
        public static string ToStringValue(this Enum value)
        {
            StringValueAtribute[] attributes = (StringValueAtribute[])value.GetType().GetField(value.ToString()).GetCustomAttributes(typeof(StringValueAtribute), false);
            return ((attributes != null) && (attributes.Length > 0)) ? attributes[0].StringValue : value.ToString();
        }

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
