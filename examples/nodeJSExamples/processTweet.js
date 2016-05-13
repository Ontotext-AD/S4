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

var https = require("https");

var inputString = "{\"text\":\"Nearly 200,000 people have been killed in #Syria " +
            "since the start of the conflict in 2011, according to " +
            "the U.N. http://t.co/pK7t8AD7Xf\"," +
            "\"lang\":\"en\",\"entities\":{\"symbols\":[]," +
            "\"urls\":[{\"expanded_url\":\"http://on.wsj.com/1pZmkY9\"," +
            "\"indices\":[112,134],\"display_url\":\"on.wsj.com/1pZmkY9\"," +
            "\"url\":\"http://t.co/pK7t8AD7Xf\"}]," +
            "\"hashtags\":[{\"text\":\"Syria\",\"indices\":[42,48]}]," +
            "\"user_mentions\":[]}," +
            "\"id\":502743846716207104," +
            "\"created_at\":\"Fri Aug 22 09:07:28   000 2014\"," +
            "\"id_str\":\"502743846716207104\"}";

var postData = {
    "document": inputString,
    "documentType": "text/x-json-twitter"
};

var dataString = JSON.stringify(postData);

var headers = {
    "Content-Type": "application/json",
    "Accept": "application/json"
};

var options = {
    hostname: "text.s4.ontotext.com",
    path: "/v1/twitie",
    method: "POST",
    auth: "<s4 api key>:<s4 key secret>",
    headers: headers,
    encoding: "utf-8"
};

// Setup the request.  The options parameter is
// the object we defined above.
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

req.on("error", function (e) {
    console.log(e);
});

req.write(dataString,"utf-8");
req.end();
