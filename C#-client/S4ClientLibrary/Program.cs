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
using Ontotext.S4.catalog;
using Ontotext.S4.service;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

/// <summary>
/// This class preforms an important function
/// </summary>

namespace Ontotext.S4
{
    class Program
    {
        static void Main(string[] args)
        {
            S4ServiceClient s4 = new S4ServiceClient(ServiceCatalog.getItem("news"), "<api-key>", "<api-pass>");
         //   System.Console.Write(s4.annotateDocumentFromUrl(new Uri("http://www.bbc.com/news/uk-politics-29784493"), SupportedMimeType.HTML).text);
            Stream st = s4.annotateDocumentAsStream("We will always remember the courage of those who served on our behalf.", SupportedMimeType.PLAINTEXT, ResponseFormat.GATE_XML);
            StreamReader str = new StreamReader(st);
            Console.WriteLine(str.ReadToEnd());
            Console.Read();
        }
    }
}
