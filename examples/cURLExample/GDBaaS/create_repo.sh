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

CONFIG="{
   \"repositoryID\" : \"<repo-name>\",
   \"label\" : \"Description of my repository\",
   \"ruleset\" : \"owl-horst-optimized\"
}"

API_KEY="<s4-api-key>"
KEY_SECRET="<s4-key-secret>"

USER="<user-id>"
DATABASE="<db-id>"
REPOSITORY_ID="<repo-name>" # same as repositoryID in $CONFIG
SERVICE_ENDPOINT="https://$API_KEY:$KEY_SECRET@rdf.s4.ontotext.com/$USER/$DATABASE"

curl -X PUT -H "Content-Type:application/json" -d "$CONFIG" $SERVICE_ENDPOINT/repositories/$REPOSITORY_ID