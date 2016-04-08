# S4 Python3 client library
# Copyright 2016 Ontotext AD
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


# import sys
# import os
import requests
import json
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

        allParams = ["body"]

        params = locals()
        for (key, val) in params["kwargs"].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method create_repository".format(key))
            params[key] = val
        del params["kwargs"]

        queryParams = {}
        headerParams = {"Content-Type": "application/json"}

        postData = (params["body"] if "body" in params else None)

        req = requests.put(
            self.apiClient.endpoint + "/repositories",
            auth=(self.apiClient.api_key, self.apiClient.key_secret),
            headers=headerParams,
            data=json.dumps(postData))

        return req.content.decode("utf-8")

    def get_repo_config(self, **kwargs):
        """
        Get information about repository cache percentage distribution

        Returns:
            Cache configuration percentage for all repositories in the database
        """

        allParams = []

        params = locals()
        for (key, val) in params["kwargs"].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method get_repo_config".format(key))
            params[key] = val
        del params["kwargs"]

        queryParams = {}
        headerParams = {"Content-Type": "application/json"}

        postData = (params["body"] if "body" in params else None)

        req = requests.get(
            self.apiClient.endpoint + "/settings/cache",
            auth=(self.apiClient.api_key, self.apiClient.key_secret),
            headers=headerParams,
            data=postData)

        return req.content.decode("utf-8")

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

        allParams = ["body"]

        params = locals()
        for (key, val) in params["kwargs"].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method update_repo_config".format(key))
            params[key] = val
        del params["kwargs"]

        queryParams = {}
        headerParams = {"Content-Type": "application/json"}

        postData = (params["body"] if "body" in params else None)

        req = requests.put(
            self.apiClient.endpoint + "/settings/cache",
            auth=(self.apiClient.api_key, self.apiClient.key_secret),
            headers=headerParams,
            data=postData)

        return req.content.decode("utf-8")

    def upload_data_file(self, file_path, **kwargs):  # RDFLib
        """
        Upload data from an external rdf file

        Args:
            file_path: String. Full path to the triples-containing file

        Kwargs:
            repo_name: String. Name of repository to upload data to
        """

        allParams = ["repo_name"]

        params = locals()
        for (key, val) in params["kwargs"].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method upload_data_file".format(key))
            params[key] = val
        del params["kwargs"]

        repo_name = (params["repo_name"] if "repo_name" in params else None)

        resourcePath = (self.apiClient.endpoint +
                        "/repositories/{}/statements".format(repo_name))
        method = "POST"

        queryParams = {}
        headerParams = {"Content-Type": "application/rdf+xml;charset=UTF-8"}

        with open(file_path, "rb") as rdf:
            postData = rdf
            response = requests.post(
                resourcePath,
                headers=headerParams,
                data=postData,
                auth=(self.apiClient.api_key, self.apiClient.key_secret))

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

        allParams = ["body", "repo_name"]

        params = locals()
        for (key, val) in params["kwargs"].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method sparql_select".format(key))
            params[key] = val
        del params["kwargs"]

        repo_name = (params["repo_name"] if "repo_name" in params else None)
        resourcePath = (self.apiClient.endpoint +
                        "/repositories/{}".format(repo_name))
        method = "POST"

        queryParams = {}
        headerParams = {"Accept": "application/sparql-results+json"}

        postData = (params["body"] if "body" in params else None)

        response = requests.post(
            resourcePath,
            headers=headerParams,
            data=postData,
            auth=(self.apiClient.api_key, self.apiClient.key_secret))

        return response.content.decode("utf-8")

    def sparql_update(self, **kwargs):
        """
        Execute SPARQL Update query

        Kwargs:
            body: Dict. Payload data to be sent with the request (required)
            repo_name: String. Name of repository to upload data to
        """

        allParams = ["body", "repo_name"]

        params = locals()
        for (key, val) in params["kwargs"].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method sparql_update".format(key))
            params[key] = val
        del params["kwargs"]

        repo_name = (params["repo_name"] if "repo_name" in params else None)
        resourcePath = (self.apiClient.endpoint +
                        "/repositories/{}/statements".format(repo_name))
        method = "POST"

        queryParams = {}
        headerParams = {"Content-Type": "application/x-www-form-urlencoded",
                        "Accept": "application/sparql-results+json"}

        postData = (params["body"] if "body" in params else None)

        response = requests.post(
            resourcePath,
            headers=headerParams,
            data=postData,
            auth=(self.apiClient.api_key, self.apiClient.key_secret))

        return response.content

    def delete_repository(self, **kwargs):
        """
        Delete repository and all of its data from database

        Kwargs:
            repo_name: String. Name of repository to be deleted
        """

        allParams = ["repo_name"]

        params = locals()
        for (key, val) in params["kwargs"].items():
            if key not in allParams:
                raise TypeError("Got an unexpected keyword argument {}" +
                                " to method sparql_update".format(key))
            params[key] = val
        del params["kwargs"]

        repo_name = (params["repo_name"] if "repo_name" in params else None)
        resourcePath = (self.apiClient.endpoint +
                        "/repositories/{}".format(repo_name))

        postData = (params["body"] if "body" in params else None)

        req = requests.delete(
            resourcePath,
            auth=(self.apiClient.api_key, self.apiClient.key_secret))
