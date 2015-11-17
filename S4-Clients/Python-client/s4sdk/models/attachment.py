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


class Attachment:
    def __init__(self):
        self.swaggerTypes = {
            'headers': 'javax.ws.rs.core.MultivaluedMap<java.lang.String, ' +
            'java.lang.String>',
            'object': 'Object',
            'content_type': 'MediaType',
            'data_handler': 'DataHandler',
            'content_id': 'str',
            'content_disposition': 'ContentDisposition'
        }

        self.headers = None
        self.object = None  # Object
        self.content_type = None  # MediaType
        self.data_handler = None  # DataHandler
        self.content_id = None  # str
        self.content_disposition = None  # ContentDisposition
