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
using System.IO;
using System.Data;
using System.Json;
using System.Runtime.InteropServices;

using Ontotext.S4.client;
using Ontotext.S4.catalog;
using Ontotext.S4.service;
using Ontotext.S4.common;

using Newtonsoft.Json;
using System.Net;
using Ontotext.S4.ontotext.service;
using S4ClientApi.Service;

namespace Ontotext.S4.service
{
    /// <summary>
    /// Main entry point for the S4 text analytics APIs to send individual documents for annotation by a particular pipeline and receive the results immediately.
    /// </summary>
    public class S4ServiceClient
    {


        public static void main(String[] args)
        {
            if (args == null || args.Length == 0)
            {
                printUsageAndTerminate(null);
            }
            Parameters parameters = new Parameters(args);
            String serviceID = parameters.getValue("service");
            if (serviceID == null)
            {
                printUsageAndTerminate("No service name provided");

            }
            ServiceDescriptor service = null;
            try
            {
                service = ServiceCatalog.getItem(serviceID);
            }
            catch (NotSupportedException nse)
            {
                printUsageAndTerminate("Unsupported service '" + serviceID + '\'');
                Console.WriteLine(nse.Message);
            }
            SupportedMimeType mimetype = SupportedMimeType.PLAINTEXT;
            if (parameters.getValue("dtype") != null)
            {
                try
                {
                    mimetype = NumToEnum<SupportedMimeType>(parameters.getValue("dtype").ToString());
                }
                catch (ArgumentException ae)
                {
                    printUsageAndTerminate("Unsupported document type (dtype) : " + parameters.getValue("dtype"));
                    Console.WriteLine(ae.Message);
                }
            }
            String inFile = parameters.getValue("file");
            String url = parameters.getValue("url");
            String outFile = parameters.getValue("out", "result.txt");

            if (inFile != null)
            {
                if (false == new FileStream(inFile, FileMode.OpenOrCreate).CanRead)
                {
                    printUsageAndTerminate("Input file is not found : " + inFile);
                }
            }
            else
            {
                if (url == null)
                {
                    printUsageAndTerminate("Neither input file, nor remote URL provided");
                }
            }

            Dictionary<String, String> creds = readCredentials(parameters);
            if (false == creds.ContainsKey("apikey")
                    || false == creds.ContainsKey("secret"))
            {
                printUsageAndTerminate("No credentials details found");
            }

            S4AnnotationClientImpl client = new S4AnnotationClientImpl(service, creds["apikey"], creds["secret"]);

            try
            {
                Stream resultData = (inFile != null) ?
                        client.annotateDocumentAsStream(new FileStream(inFile, FileMode.OpenOrCreate), CharSet.Unicode.ToString(), mimetype, ResponseFormat.JSON)
                        : client.annotateDocumentAsStream(new Uri(url), mimetype, ResponseFormat.JSON);

                FileStream outStream = new FileStream(outFile, FileMode.Create);
                resultData.CopyTo(outStream);

                outStream.Close();
                resultData.Close();
            }
            catch (IOException ioe)
            {
                Console.WriteLine(ioe.Message);
                Environment.Exit(1);
            }

        }


        public static T NumToEnum<T>(String number)
        {
            return (T)Enum.ToObject(typeof(T), number);
        }

        private static void printUsageAndTerminate(String error)
        {
            if (error != null)
            {
                Console.WriteLine(error);
            }
            Console.WriteLine("Usage: S4ClientService parameter1=value1 parameter2=value2 ...");
            Console.WriteLine("Parameters:");
            Console.WriteLine("  service - the service id to be used (one of:'TwitIE', 'SBT' and 'news')");
            Console.WriteLine("  file    - input file path");
            Console.WriteLine("  url     - input document URL");
            Console.WriteLine("  dtype   - the MIME type of the document (one of:'text/plain', 'text/html', 'application/xml', 'text/xml', 'text/x-pubmed', 'text/x-pubmed', 'text/x-cochrane', 'text/x-mediawiki', 'text/x-json-twitter')");
            Console.WriteLine("  out     - result file name. Defaults to 'result.txt'");
            Console.WriteLine("  apikey  - the api key if credentials file is not used");
            Console.WriteLine("  secret  - the api secret if credentials file is not used");
            Console.WriteLine("  creds   - credentails file path (if apikey and secret parameters are not used)");
            Environment.Exit(1);

        }

        private static Dictionary<String, String> readCredentials(Parameters parameters)
        {
            Dictionary<String, String> dictionary = new Dictionary<String, String>();
            if (parameters.getValue("apikey") != null)
            {
                if (parameters.getValue("secret") == null)
                {
                    printUsageAndTerminate("API key secret not provided");
                }
                dictionary.Add("apikey", parameters.getValue("apikey"));
                dictionary.Add("secret", parameters.getValue("secret"));
                return dictionary;
            }
            String credsFile = "s4credentials.properties";
            if (parameters.getValue("creds") != null)
            {
                credsFile = parameters.getValue("creds");
            }
            if (new FileStream(credsFile, FileMode.OpenOrCreate).CanRead)
            {
                try
                {
                    foreach (KeyValuePair<String, String> pair in load(new FileStream(credsFile, FileMode.OpenOrCreate)))
                    {
                        dictionary.Add(pair.Key, pair.Value);
                    }
                }
                catch (IOException ex)
                {
                    printUsageAndTerminate("Error reading credentials file: " + ex.Message);
                }
            }
            else
            {
                try
                {
                    foreach (KeyValuePair<String, String> pair in load(new FileStream(credsFile, FileMode.Open)))
                    {

                    }
                }
                catch (IOException ioe)
                {
                    printUsageAndTerminate("Error reading credentials file: " + ioe.Message);
                }
            }
            var data = new Dictionary<string, string>();
            foreach (var row in File.ReadAllLines(credsFile))
                data.Add(row.Split('=')[0], string.Join("=", row.Split('=').Skip(1).ToArray()));
            return data;
        }

        private static IEnumerable<KeyValuePair<String, String>> load(FileStream fileStream)
        {
            var data = new Dictionary<string, string>();
            foreach (var row in File.ReadAllLines(fileStream.Name))
                data.Add(row.Split('=')[0], string.Join("=", row.Split('=').Skip(1).ToArray()));
            return data;
        }


        public static Dictionary<string, string> GetProperties(string path)
        {
            string fileData = "";
            using (StreamReader sr = new StreamReader(path))
            {
                fileData = sr.ReadToEnd().Replace("\r", "");
            }
            Dictionary<string, string> Properties = new Dictionary<string, string>();
            string[] kvp;
            string[] records = fileData.Split("\n".ToCharArray());
            foreach (string record in records)
            {
                kvp = record.Split("=".ToCharArray());
                Properties.Add(kvp[0], kvp[1]);
            }
            return Properties;
        }
    }
}
