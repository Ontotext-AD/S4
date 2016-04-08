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


class DataFlavor:
    def __init__(self):
        self.swaggerTypes = {
            "human_presentable_name": "str",
            "sub_type": "str",
            "mime_type": "str",
            "default_representation_class_as_string": "str",
            "primary_type": "str",
            "flavor_java_file_list_type": "bool",
            "flavor_remote_object_type": "bool",
            "flavor_serialized_object_type": "bool",
            "flavor_text_type": "bool",
            "mime_type_serialized_object": "bool",
            "representation_class_byte_buffer": "bool",
            "representation_class_char_buffer": "bool",
            "representation_class_input_stream": "bool",
            "representation_class_reader": "bool",
            "representation_class_remote": "bool",
            "representation_class_serializable": "bool"
        }

        self.human_presentable_name = None  # str
        self.sub_type = None  # str
        self.mime_type = None  # str
        self.default_representation_class_as_string = None  # str
        self.primary_type = None  # str
        self.flavor_java_file_list_type = None  # bool
        self.flavor_remote_object_type = None  # bool
        self.flavor_serialized_object_type = None  # bool
        self.flavor_text_type = None  # bool
        self.mime_type_serialized_object = None  # bool
        self.representation_class_byte_buffer = None  # bool
        self.representation_class_char_buffer = None  # bool
        self.representation_class_input_stream = None  # bool
        self.representation_class_reader = None  # bool
        self.representation_class_remote = None  # bool
        self.representation_class_serializable = None  # bool
