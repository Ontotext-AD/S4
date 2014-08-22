#!/bin/bash

# Url of the endpoint
PROTOCOL="https://"
ENDPOINT_URL="text.s4.ontotext.com/v1/"
SERVICE_ID="twitie"

# API Key
KEY_ID="<your-credentials-here>"
PASSWORD="<your-credentials-here>"

# Headers
ACCEPT="application/gate+json"
CONTENT_TYPE="application/json"

# POST body parameters
DOCUMENT="{\\\"text\\\":\\\"Nearly 200,000 people have been killed in #Syria since the start of the conflict in 2011, according to the U.N. http://t.co/pK7t8AD7Xf\\\",\\\"lang\\\":\\\"en\\\",\\\"entities\\\":{\\\"symbols\\\":[],\\\"urls\\\":[{\\\"expanded_url\\\":\\\"http://on.wsj.com/1pZmkY9\\\",\\\"indices\\\":[112,134],\\\"display_url\\\":\\\"on.wsj.com/1pZmkY9\\\",\\\"url\\\":\\\"http://t.co/pK7t8AD7Xf\\\"}],\\\"hashtags\\\":[{\\\"text\\\":\\\"Syria\\\",\\\"indices\\\":[42,48]}],\\\"user_mentions\\\":[]},\\\"id\\\":502743846716207104,\\\"created_at\\\":\\\"Fri Aug 22 09:07:28 +0000 2014\\\",\\\"id_str\\\":\\\"502743846716207104\\\"}"
DOCUMENT_MIME_TYPE="text/x-json-twitter"
ANNOTATION_SELECTORS="[\":\", \"Original markups:\"]"
JSON_BODY="{\"document\" : \"$DOCUMENT\", \"documentType\" : \"$DOCUMENT_MIME_TYPE\", \"annotationSelectors\" : $ANNOTATION_SELECTORS}"

echo -e "Processing an embedded plain text document...\n"
echo -e "Request body is: "
echo -e $JSON_BODY
echo -e "\n"

PIPELINE_ENDPOINT="$PROTOCOL$KEY_ID:$PASSWORD@$ENDPOINT_URL$SERVICE_ID"
echo -e "Pipeline endpoint is:"
echo -e "$PIPELINE_ENDPOINT\n"

curl -X POST -w "\n\n\nContent-Type:%{content_type}\nHTTP Code: %{http_code}\n" -H "Content-Type: $CONTENT_TYPE" -H "Accept: $ACCEPT" -d "$JSON_BODY" $PIPELINE_ENDPOINT

