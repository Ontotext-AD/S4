# Self-Service Semantic Suite
# Copyright (c) 2014, Ontotext AD, All rights reserved.

# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 3.0 of the License, or (at your option) any later version.

# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.

# You should have received a copy of the GNU Lesser General Public
# License along with this library.

#!/bin/bash

PROTOCOL="https://"
ENDPOINT_URL="text.s4.ontotext.com/v1/"

# API Key
KEY_ID="<your-credentials-here>"
PASSWORD="<your-credentials-here>"


echo "Testing endpoint..."
curl -w "\n\n\nContent-Type:%{content_type}\nHTTP Code: %{http_code}\n" $PROTOCOL$KEY_ID:$PASSWORD@$ENDPOINT_URL
