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
CONTENT_TYPE="application/x-www-form-urlencoded"

# POST body parameters
SPARQL_UPDATE="PREFIX dc: <http://purl.org/dc/elements/1.1/> INSERT DATA { <http://example/egbook> dc:title  \"This is an example title\" }"

USER_ID="<user-id>"
DATABASE="<db-id>"
REPOSITORY="<repo-name>"
SERVICE_ENDPOINT="https://$API_KEY:$KEY_SECRET@rdf.s4.ontotext.com/$USER_ID/$DATABASE"

curl -X POST -w "\n\n\nContent-Type:%{content_type}\nHTTP Code: %{http_code}\n" -H "Content-Type: $CONTENT_TYPE" -d "update=$SPARQL_UPDATE" $SERVICE_ENDPOINT/repositories/$REPOSITORY/statements