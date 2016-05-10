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

# Note that this method works only for evaluation of small graphs and
# PUBLIC-ONLY repositories. For SPARQL updates and non-public and fully
# remote repository support - refer to the other example files

import rdflib

g = rdflib.Graph()
g.load("https://rdf.s4.ontotext.com/<user-id>/<database-name>" +
       "/repositories/<repo-name>/statements")

query = """SELECT * WHERE { ?s ?p ?o } LIMIT 10"""

for row in g.query(query):
    print (row.s)
    print (row.p)
    print (row.o)
    print ()
