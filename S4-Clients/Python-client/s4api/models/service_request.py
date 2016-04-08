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


class ServiceRequest:
    def __init__(self):
        self.swaggerTypes = {
            "image_tagging": "bool",
            "image_categorization": "bool",
            "document": "str",
            "document_url": "str",
            "annotation_selectors": "list[str]",
            "document_type": "str",
            "pipeline_id": "str"
        }

        self.image_tagging = None  # bool
        self.image_categorization = None  # bool
        self.document = None  # str
        self.document_url = None  # str
        self.annotation_selectors = None  # list[str]
        self.document_type = None  # str
        self.pipeline_id = None  # str
