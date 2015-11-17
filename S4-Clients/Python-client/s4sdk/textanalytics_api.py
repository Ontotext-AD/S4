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


class TextanalyticsApi(object):

    """
    Self-Service Semantic Suite (S4) Text-Annotation Api
    """

    def __init__(self, apiClient):
        self.apiClient = apiClient

    def test(self, **kwargs):
        """
        Tests whether processing endpoint is functional.

        Returns: String
        """

        allParams = []

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError(
                    "Got an unexpected keyword argument" +
                    "{} to method test".format(key))
            params[key] = val
        del params['kwargs']

        services = ["/twitie", "/sbt", "/news"]
        test_endpoint = self.apiClient.endpoint
        for each in services:
            if each in test_endpoint:
                test_endpoint = test_endpoint[:len(test_endpoint)-len(each)]
        resourcePath = test_endpoint
        method = 'GET'

        queryParams = {}
        headerParams = {}

        postData = (params['body'] if 'body' in params else None)

        response = self.apiClient.callAPI(resourcePath, method, queryParams,
                                          postData, headerParams)

        if not response:
            return None

        # responseObject = self.apiClient.deserialize(response, 'str')
        return response

    def process(self, output_type, **kwargs):
        """
        Processes JSON notation

        Args:
            output_type - String. The output format of the results

            Example template: "json"

        Kwargs:
            body - Dictionary. Data, passed with request body

            Example template:
            {
                "document": "your text here",
                "documentType": "text/plain"
            }

        Returns:
            JSON structure with annotated text
        """

        allParams = ['body']

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError(
                    "Got an unexpected keyword argument" +
                    " {} to method process".format(key))
            params[key] = val
        del params['kwargs']

        resourcePath = self.apiClient.endpoint

        method = 'POST'

        queryParams = {}
        json_head = {"Accept": "application/json",
                     "Content-type": "application/json"}
        xml_head = {"Accept": "application/gate+xml",
                    "Content-type": "application/json"}
        if output_type == "json":
            headerParams = json_head
        elif output_type == "xml":
            headerParams = xml_head
        else:
            raise ValueError("Please enter a valid output type.")

        postData = (params['body'] if 'body' in params else None)

        response = self.apiClient.callAPI(resourcePath, method, queryParams,
                                          postData, headerParams)
        return response

    def process_multipart_request(self, data, **kwargs):
        """
        Processes multipart data.

        Args:
            data - Dictionary. Contains binary file data and metadata

            Example template:

            {
                "meta": ('', {\"documentType\": "application/msword"}, "application/json"),
                "data": ("name of MSWORD file", binary-content, "application/octet-stream")
            }

        Returns: XML structure with annotated text from file
        """

        allParams = []

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError(
                    "Got an unexpected keyword argument {} " +
                    "to method process_multipart_request".format(key))
            params[key] = val
        del params['kwargs']

        resourcePath = self.apiClient.endpoint
        method = 'POST'

        queryParams = {}
        headerParams = {"Content-Type": "multipart/mixed",
                        "Accept": "application/gate+xml"}
        postData = (params['body'] if 'body' in params else None)

        response = requests.post(
            resourcePath,
            auth=(self.apiClient.apiKey, self.apiClient.apiSecret),
            files=data)

        return response.content
