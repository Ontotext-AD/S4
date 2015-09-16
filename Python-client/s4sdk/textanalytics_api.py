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

from .models import *

import requests


TEXT_ENDPOINT = "https://text.s4.ontotext.com/v1"


class TextanalyticsApi(object):

    def __init__(self, apiClient):
        self.apiClient = apiClient

    def test(self, **kwargs):
        """Tests whether procesing endpoint is functional. / Returns: String

        Args:
        url - Processing endpoint (https://text.s4.ontotext.com/v1)

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

        resourcePath = TEXT_ENDPOINT
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

    def process_json(self, service, **kwargs):
        """Processes JSON notation / Returns JSON

        Args:
        url - Processing endpoint (https://text.s4.ontotext.com/v1)
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

        resourcePath = TEXT_ENDPOINT + "/" + service

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

    def process_for_xml_output(self, service, **kwargs):
        """Processes JSON notation / Returns XML structure

        Args:
        url - Processing endpoint (https://text.s4.ontotext.com/v1)
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

        resourcePath = TEXT_ENDPOINT + "/" + service
        method = 'POST'

        queryParams = {}
        headerParams = {"Accept": "application/gate+xml",
                        "Content-Type": "application/json"}

        postData = (params['body'] if 'body' in params else None)

        response = self.apiClient.callAPI(resourcePath, method, queryParams,
                                          postData, headerParams)
        return response

    def process_multipart_request(self, service, data, **kwargs):
        """Processes multipart and/or mixed data. / Returns: XML structure

        Args:
        url - Processing endpoint (https://text.s4.ontotext.com/v1)
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

        resourcePath = TEXT_ENDPOINT + "/" + service
        method = 'POST'

        queryParams = {}
        headerParams = {"Content-Type": "multipart/form-data",
                        "Accept": "application/gate+xml"}
        postData = (params['body'] if 'body' in params else None)

        # Unfortunately there is no easy way to perform a multipart request
        # with urllib, so we're using python-requests instead
        response = requests.post(
            resourcePath,
            auth=(self.apiClient.apiKey, self.apiClient.apiSecret),
            files=data)

        return response.content

        # response = self.apiClient.callAPI(resourcePath, method, queryParams,
        #                                   postData, headerParams)
