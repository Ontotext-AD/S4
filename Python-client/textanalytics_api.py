"""
TextanalyticsApi.py
Copyright 2014, Ontotext AD

This file is free software; you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation; either version 2.1 of the License, or (at your option)
any later version.
This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.
You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

"""
# import sys
# import os

from models import *
from swagger import ApiClient


class TextanalyticsApi(object):

    def __init__(self, apiClient):
        self.apiClient = apiClient

    def process_json(self, url, service, **kwargs):
        """Processes JSON notation / Returns JSON

        Args:
        url - Processing endpoint
        service - Text processing service (twitie, news or sbt)

        """
        allParams = ['json_shop_item_id', 'body', 'accept_encoding']

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError(
                    "Got an unexpected keyword argument" +
                    " {} to method process_json".format(key))
            params[key] = val
        del params['kwargs']

        resourcePath = url + "/" + service

        method = 'POST'

        queryParams = {}
        headerParams = {"Accept": "application/json",
                        "Content-type": "application/json"}

        if ('accept_encoding' in params):
            headerParams['accept_encoding'] = params['accept_encoding']

        postData = (params['body'] if 'body' in params else None)

        response = self.apiClient.callAPI(resourcePath, method, queryParams,
                                          postData, headerParams)
        return response

    def process_for_xml_output(self, url, service, **kwargs):
        """Processes JSON notation / Returns XML structure

        Args:
        url - Processing endpoint
        service - Text processing service (twitie, news or sbt)

        """

        allParams = ['xml_shop_item_id', 'body', 'accept_encoding']

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError(
                    "Got an unexpected keyword argument {} " +
                    "to method process_for_xml_output".format(key))
            params[key] = val
        del params['kwargs']

        resourcePath = url + "/" + service
        method = 'POST'

        queryParams = {}
        headerParams = {"Accept": "application/gate+xml",
                        "Content-Type": "application/json"}

        postData = (params['body'] if 'body' in params else None)

        response = self.apiClient.callAPI(resourcePath, method, queryParams,
                                          postData, headerParams)
        return response

    def test(self, url, **kwargs):
        """Tests whether procesing endpoint is functional. / Returns: String

        Args:
        url - Processing endpoint

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

        resourcePath = url
        method = 'GET'

        queryParams = {}
        headerParams = {}

        postData = (params['body'] if 'body' in params else None)

        response = self.apiClient.callAPI(resourcePath, method, queryParams,
                                          postData, headerParams)

        if not response:
            return None

        responseObject = self.apiClient.deserialize(response, 'str')
        return response

    def process_multipart_request(self, url, service, **kwargs):
        """Processes multipart and/or mixed data. / Returns:

        Args:
        url - Processing endpoint
        service - Text processing service (twitie, news or sbt)

        """

        allParams = ['mix_shop_item_id', 'body', 'accept_encoding', 'accept']

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError(
                    "Got an unexpected keyword argument {} " +
                    "to method process_multipart_request".format(key))
            params[key] = val
        del params['kwargs']

        resourcePath = url + "/" + service
        method = 'POST'

        queryParams = {}
        headerParams = {"Content-Type": "application/octet-stream"}
        postData = (params['body'] if 'body' in params else None)

        response = self.apiClient.callAPI(resourcePath, method, queryParams,
                                          postData, headerParams)


# SAMPLE CODE USAGE
client = ApiClient(
    apiKey="s4ts9m036a8b", apiSecret="6lucecm2t73558v")
accessor = TextanalyticsApi(client)

# TEST
print(accessor.test("https://text.s4.ontotext.com/v1"))

# PROCESS JSON
payload = {"document": "sample text here", "documentType": "text/plain"}
print(accessor.process_json(
    "https://text.s4.ontotext.com/v1", "twitie",
    body=payload))

# PROCESS FOR XML OUTPUT
payload2 = {"document": "sample text here", "documentType": "text/plain"}
print(accessor.process_for_xml_output(
    "https://text.s4.ontotext.com/v1", "twitie",
    body=payload2))

with open("sample.docx", 'rb') as f:
    content = f.read()
payload3 = {"documentType": "application/msword", "file": content}
print(accessor.process_multipart_request(
    "https://text.s4.ontotext.com/v1", "twitie",
    body=payload3))
