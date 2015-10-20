# Copyright 2014, Ontotext AD

# This file is free software; you can redistribute it and/or modify it under
# the terms of the GNU Lesser General Public License as published by the Free
# Software Foundation; either version 2.1 of the License, or (at your option)
# any later version.
# This library is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
# FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
# details.
# You should have received a copy of the GNU Lesser General Public License
# along with this library; if not, write to the Free Software Foundation, Inc.,
# 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA


# import sys
# import os
import requests
from .models import *


class GraphDBApi(object):

    """
    Self-Service Semantic Suite (S4) GraphDB Api
    """

    def __init__(self, apiClient):
        self.apiClient = apiClient

    def create_repository(self, **kwargs):
        """
        Create new repository in database

        Kwargs:
            body: Payload data to be sent with the request. (required)

            Example template:

            {
                "repositoryID": "Name of your repository",
                "label": "Description of your database",
                "ruleset": "owl-horst-optimized"
            }
        """

        allParams = ['body']

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method create_repository".format(key))
            params[key] = val
        del params['kwargs']

        resourcePath = self.apiClient.endpoint + "/repositories"
        method = 'PUT'

        queryParams = {}
        headerParams = {"Content-Type": "application/json"}

        postData = (params['body'] if 'body' in params else None)

        response = self.apiClient.callAPI(resourcePath, method, queryParams,
                                          postData, headerParams)
        return response

    def get_repo_config(self, **kwargs):
        """
        Get information about repository cache percentage distribution

        Returns:
            Cache configuration percentage for all repositories in the database
        """

        allParams = []

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method get_repo_config".format(key))
            params[key] = val
        del params['kwargs']

        resourcePath = self.apiClient.endpoint + "/settings/cache"
        method = 'GET'

        queryParams = {}
        headerParams = {"Content-Type": "application/json"}

        postData = (params['body'] if 'body' in params else None)

        response = self.apiClient.callAPI(resourcePath, method, queryParams,
                                          postData, headerParams)
        return response

    def update_repo_config(self, **kwargs):
        """
        Update repositories configuration percentage.
        Distribution sum must be equal to 100 (%)

        Kwargs:
            body: Dict. Payload data to be sent with the request (required)

            Example template:

            {
                "proportions": [
                    {"repositoryID": "test-repo1",
                     "percentage": 45},
                    {"repositoryID": test-repo2,
                        "percentage": 55}
                ]
            }
        """

        allParams = ['body']

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method update_repo_config".format(key))
            params[key] = val
        del params['kwargs']

        resourcePath = self.apiClient.endpoint + "/settings/cache"
        method = 'PUT'

        queryParams = {}
        headerParams = {"Content-Type": "application/json"}

        postData = (params['body'] if 'body' in params else None)

        response = self.apiClient.callAPI(resourcePath, method, queryParams,
                                          postData, headerParams)
        return response

    def upload_data_file(self, file_path, **kwargs):  # RDFLib
        """
        Upload data from an external .rdf file

        Args:
            file_path: String. Full path to the triples-containing file

        Kwargs:
            repo_name: String. Name of repository to upload data to
        """

        allParams = ['repo_name']

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method upload_data_file".format(key))
            params[key] = val
        del params['kwargs']

        repo_name = (params['repo_name'] if 'repo_name' in params else None)

        resourcePath = (self.apiClient.endpoint
                        + "/repositories/{}/statements".format(repo_name))
        method = 'POST'

        queryParams = {}
        headerParams = {"Content-Type": "application/rdf+xml;charset=UTF-8"}

        with open(file_path, "rb") as rdf:
            postData = rdf
            response = requests.post(
                resourcePath,
                headers=headerParams,
                data=postData,
                auth=(self.apiClient.apiKey, self.apiClient.apiSecret))

        return response.content

    def sparql_select(self, **kwargs):
        """
        Execute SPARQL Select query

        Kwargs:
            body: Dict. Payload data to be sent with the request (required)
            repo_name: String. Name of repository to upload data to

        Returns:
            XML structure with results of SPARQL Select query
        """

        allParams = ['body', 'repo_name']

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method sparql_select".format(key))
            params[key] = val
        del params['kwargs']

        repo_name = (params['repo_name'] if 'repo_name' in params else None)
        resourcePath = (self.apiClient.endpoint
                        + "/repositories/{}".format(repo_name))
        method = 'POST'

        queryParams = {}
        headerParams = {"Accept": "application/sparql-results+xml"}

        postData = (params['body'] if 'body' in params else None)

        response = requests.post(
            resourcePath,
            headers={'Accept': 'application/sparql-results+xml'},
            data=postData,
            auth=(self.apiClient.apiKey, self.apiClient.apiSecret))

        return response.content

    def sparql_update(self, **kwargs):
        """
        Execute SPARQL Update query

        Kwargs:
            body: Dict. Payload data to be sent with the request (required)
            repo_name: String. Name of repository to upload data to
        """

        allParams = ['body', 'repo_name']

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method sparql_update".format(key))
            params[key] = val
        del params['kwargs']

        repo_name = (params['repo_name'] if 'repo_name' in params else None)
        resourcePath = (self.apiClient.endpoint
                        + "/repositories/{}/statements".format(repo_name))
        method = 'POST'

        queryParams = {}
        headerParams = {"Content-Type": "application/x-www-form-urlencoded"}

        postData = (params['body'] if 'body' in params else None)

        response = requests.post(
            resourcePath,
            headers={'Accept': 'application/sparql-results+xml'},
            data=postData,
            auth=(self.apiClient.apiKey, self.apiClient.apiSecret))

        return response.content

    def delete_repository(self, **kwargs):
        """
        Delete repository and all of its data from database

        Kwargs:
            repo_name: String. Name of repository to upload data to
        """

        allParams = ['repo_name']

        params = locals()
        for (key, val) in params['kwargs'].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method sparql_update".format(key))
            params[key] = val
        del params['kwargs']

        repo_name = (params['repo_name'] if 'repo_name' in params else None)
        resourcePath = (self.apiClient.endpoint
                        + "/repositories/{}".format(repo_name))
        method = 'DELETE'

        queryParams = {}
        headerParams = {}

        postData = (params['body'] if 'body' in params else None)

        response = self.apiClient.callAPI(resourcePath, method, queryParams,
                                          postData, headerParams)

        return response
