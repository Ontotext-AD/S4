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

PROTOCOL="https://"
ENDPOINT_URL="text.s4.ontotext.com/v1/"
SERVICE_ID="news"

# API Key
KEY_ID="<s4-api-key>"
PASSWORD="<s4-key-secret>"

# Headers
ACCEPT="application/json"
CONTENT_TYPE="application/json"

# POST body parameters
DOCUMENT="<your-text-here>"
DOCUMENT_MIME_TYPE="text/plain"
JSON_BODY="{\"document\" : \"$DOCUMENT\", \"documentType\" : \"$DOCUMENT_MIME_TYPE\"}"

echo -e "Processing an embedded plain text document...\n"
echo -e "Request body is: "
echo -e $JSON_BODY
echo -e "\n"

PIPELINE_ENDPOINT="$PROTOCOL$KEY_ID:$PASSWORD@$ENDPOINT_URL$SERVICE_ID"
echo -e "Pipeline endpoint is:"
echo -e "$PIPELINE_ENDPOINT\n"

curl -X POST -w "\n\n\nContent-Type:%{content_type}\nHTTP Code: %{http_code}\n" -H "Content-Type: $CONTENT_TYPE" -H "Accept: $ACCEPT" -d "$JSON_BODY" $PIPELINE_ENDPOINT
