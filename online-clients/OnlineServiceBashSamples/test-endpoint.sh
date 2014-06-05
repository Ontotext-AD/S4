#!/bin/bash

PROTOCOL="https://"
ENDPOINT_URL="s4.ontotext.com/online-processing/item/"

# API Key
KEY_ID="<your-credentials-here>"
PASSWORD="<your-credentials-here>"

echo "Testing endpoint..."
curl -w "\n\n\nContent-Type:%{content_type}\nHTTP Code: %{http_code}\n" $PROTOCOL$KEY_ID:$PASSWORD@$ENDPOINT_URL
