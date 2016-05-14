/*
Copyright 2016 Ontotext AD

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

var querystring = require("querystring");

var config = "{\"repositoryID\" : \"<repo-name>\", \
\"label\" : \"<Description of my repository>\", \
\"ruleset\" : \"owl-horst-optimized\"}";

var headers = {
    "Accept": "application/json",
    "Content-Type": "application/json"
};

var options = {
    hostname: "rdf.s4.ontotext.com",
    path: "/4839327863/<db-id>/repositories/<repo-name>", // same as one in the configuration string
    method: "PUT",
    auth: "<s4 api key>:<s4 key secret>",
    headers: headers
};

var https = require("https");
var req = https.request(options, function (res) {
    res.setEncoding("utf-8");

    var responseString = "";

    res.on("data", function (data) {
        responseString += data;
    });

    res.on("end", function () {
        console.log(responseString);
        console.log("\nStatus Code: ", res.statusCode);
        console.log("\nHeaders:");
        console.log(res.headers);
    });
});

req.end(config);