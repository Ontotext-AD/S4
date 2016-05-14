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


API_KEY="<s4-api-key>"
KEY_SECRET="<s4-key-secret>"

# Headers
ACCEPT="application/json"
CONTENT_TYPE="application/json"

# POST body parameters
DOCUMENT="{\\\"text\\\":\\\"Nearly 200,000 people have been killed in #Syria since the start of the conflict in 2011, according to the U.N. http://t.co/pK7t8AD7Xf\\\",\\\"lang\\\":\\\"en\\\",\\\"entities\\\":{\\\"symbols\\\":[],\\\"urls\\\":[{\\\"expanded_url\\\":\\\"http://on.wsj.com/1pZmkY9\\\",\\\"indices\\\":[112,134],\\\"display_url\\\":\\\"on.wsj.com/1pZmkY9\\\",\\\"url\\\":\\\"http://t.co/pK7t8AD7Xf\\\"}],\\\"hashtags\\\":[{\\\"text\\\":\\\"Syria\\\",\\\"indices\\\":[42,48]}],\\\"user_mentions\\\":[]},\\\"id\\\":502743846716207104,\\\"created_at\\\":\\\"Fri Aug 22 09:07:28 +0000 2014\\\",\\\"id_str\\\":\\\"502743846716207104\\\"}"
DOCUMENT_MIME_TYPE="text/x-json-twitter"
JSON_BODY="{\"document\" : \"$DOCUMENT\", \"documentType\" : \"$DOCUMENT_MIME_TYPE\"}"

PIPELINE_ENDPOINT="https://$API_KEY:$KEY_SECRET@text.s4.ontotext.com/v1/twitie"

curl -X POST -w "\n\n\nContent-Type:%{content_type}\nHTTP Code: %{http_code}\n" -H "Content-Type: $CONTENT_TYPE" -H "Accept: $ACCEPT" -d "$JSON_BODY" $PIPELINE_ENDPOINT

