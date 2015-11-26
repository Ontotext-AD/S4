# Copyright 2015 Ontotext AD

#    Licensed under the Apache License, Version 2.0 (the "License");
#    you may not use this file except in compliance with the License.
#    You may obtain a copy of the License at

#        http://www.apache.org/licenses/LICENSE-2.0

#    Unless required by applicable law or agreed to in writing, software
#    distributed under the License is distributed on an "AS IS" BASIS,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the License for the specific language governing permissions and
#    limitations under the License.


# import sys
# import os

from .models import *
import requests


class FactForgeApi(object):

    """
    Self-Service Semantic Suite (S4) FactForge API
    """

    def __init__(self, apiClient):
        self.apiClient = apiClient

    def sparql_get(self, **kwargs):
        """Execute a SPARQL query
        Execute a SPARQL 1.1 Select query on the FactForge dataset.

        Kwargs:
            accept, str: Accept header (required)
            Available Accept headers
                "application/sparql-results+json",
                "application/sparql-results+xml",
                "application/x-binary-rdf-results-table" (required)

            query, str: Syntactically valid SPARQL 1.1 Select Query (required)

            accept_encoding, str: Accept-Encoding header (optional)
            Available Accept-Encoding headers - "gzip"

        Returns:
            String - Query results, structured as the
            specified Accept-header type
        """

        allParams = ["accept", "query", "accept_encoding"]

        params = locals()
        for (key, val) in params["kwargs"].items():
            if key not in allParams:
                raise TypeError(
                    "Got an unexpected keyword argument" +
                    " {} to method sparql_get".format(key))
            params[key] = val
        del params["kwargs"]

        resourcePath = self.apiClient.endpoint
        method = "GET"

        queryParams = {}
        headerParams = {}

        if ("query" in params):
            queryParams["query"] = params["query"]

        if ("accept" in params):
            headerParams["accept"] = params["accept"]

        if ("accept_encoding" in params):
            headerParams["accept_encoding"] = params["accept_encoding"]

        postData = (params["body"] if "body" in params else None)

        req = requests.get(
            resourcePath,
            auth=(self.apiClient.api_key, self.apiClient.key_secret),
            params=queryParams,
            headers=headerParams)
        # response = self.apiClient.callAPI(resourcePath, method, queryParams,
        #                                   postData, headerParams)

        return req.content.decode("utf-8")

    def sparql_post(self, **kwargs):
        """Execute a SPARQL query
        Execute a valid SPARQL 1.1 Select query on the FactForge dataset.
        This method should be used in cases where the length of the query
        exceeds practicable limits of proxies, servers, etc.

        Kwargs:
            accept, str: Accept header (required)
            Available Accept headers -
                "application/sparql-results+json",
                "application/sparql-results+xml",
                "application/x-binary-rdf-results-table"

            query, str: Syntactically valid SPARQL 1.1 Select Query (required)

            content_type, str:  (required)

            query, str:  (required)

            accept_encoding, str: Accept-Encoding header (optional)
            Available Accept-Encoding headers - "gzip"

        Returns:
            String - Query results, structured as the
            specified Accept-header type
        """

        allParams = ["accept", "content_type", "query", "accept_encoding"]

        params = locals()
        for (key, val) in params["kwargs"].items():
            if key not in allParams:
                raise TypeError(
                    "Got an unexpected keyword argument {} " +
                    "to method sparql_post".format(key))
            params[key] = val
        del params["kwargs"]

        resourcePath = self.apiClient.endpoint
        method = "POST"

        queryParams = {}
        headerParams = {}

        if ("accept" in params):
            headerParams["accept"] = params["accept"]

        if ("query" in params):
            queryParams["query"] = params["query"]

        if ("accept_encoding" in params):
            headerParams["accept_encoding"] = params["accept_encoding"]

        postData = (params["body"] if "body" in params else None)

        req = requests.post(
            resourcePath,
            auth=(self.apiClient.api_key, self.apiClient.key_secret),
            data={"query": queryParams["query"]},
            headers=headerParams)
        # response = self.apiClient.callAPI(resourcePath, method, queryParams,
        #                                   postData, headerParams)

        return req.content.decode("utf-8")
