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


class DataHandler:
    def __init__(self):
        self.swaggerTypes = {
            "data_source": "DataSource",
            "name": "str",
            "input_stream": "InputStream",
            "content": "Object",
            "content_type": "str",
            "output_stream": "OutputStream",
            "all_commands": "list[CommandInfo]",
            "preferred_commands": "list[CommandInfo]",
            "transfer_data_flavors": "list[DataFlavor]"

        }
        self.data_source = None  # DataSource
        self.name = None  # str
        self.input_stream = None  # InputStream
        self.content = None  # Object
        self.content_type = None  # str
        self.output_stream = None  # OutputStream
        self.all_commands = None  # list[CommandInfo]
        self.preferred_commands = None  # list[CommandInfo]
        self.transfer_data_flavors = None  # list[DataFlavor]
