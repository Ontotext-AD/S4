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


class MediaType:
    def __init__(self):
        self.swaggerTypes = {
            "type": "str",
            "subtype": "str",
            "parameters": "Map[string,string]",
            "wildcard_type": "bool",
            "wildcard_subtype": "bool"
        }

        self.type = None  # str
        self.subtype = None  # str
        self.parameters = None  # Map[string,string]
        self.wildcard_type = None  # bool
        self.wildcard_subtype = None  # bool
