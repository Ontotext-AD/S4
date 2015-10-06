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


class ServiceRequest:
    """
    Swagger-codegen - generated model class ServiceRequest
    """
    def __init__(self):
        self.swaggerTypes = {
            'image_tagging': 'bool',
            'image_categorization': 'bool',
            'document': 'str',
            'document_url': 'str',
            'annotation_selectors': 'list[str]',
            'document_type': 'str',
            'pipeline_id': 'str'
        }

        self.image_tagging = None  # bool
        self.image_categorization = None  # bool
        self.document = None  # str
        self.document_url = None  # str
        self.annotation_selectors = None  # list[str]
        self.document_type = None  # str
        self.pipeline_id = None  # str
