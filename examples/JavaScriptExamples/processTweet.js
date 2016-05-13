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

(function () {
    window.addEventListener("load", function sendingTweetForProcessing() {
        var username = "<s4-api-key>";
        var password = "<s4-key-secret>";
        var url = "https://text.s4.ontotext.com/v1/twitie";
        var method = "POST";

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

        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4) {
                /**
                 * Create table with response
                 * result and headers.
                 */
                var tableHolder = document.createElement("table");
                tableHolder.style.margin = "0px auto";
                var firstRow = document.createElement("tr");
                var secondRow = document.createElement("tr");
                var thirdRow = document.createElement("tr");

                /**
                 * Add left descriptive cells
                 */
                var firstDesc = document.createElement('td');
                var secondDesc = document.createElement('td');
                var thirdDesc = document.createElement('td');

                firstDesc.innerHTML = "Response headers:";
                secondDesc.innerHTML = "Response result:";
                thirdDesc.innerHTML = "Status code:";

                firstRow.appendChild(firstDesc);
                secondRow.appendChild(secondDesc);
                thirdRow.appendChild(thirdDesc);

                var responseHeadersCell = document.createElement('td');
                var responseCell = document.createElement('td');
                var statusCell = document.createElement('td');

                responseHeadersCell.innerHTML = xhr.getAllResponseHeaders();
                /**
                 * encode < and > simbols
                 */
                responseCell.innerHTML = xhr.responseText.replace(/</g, '&lt;').replace(/>/g, '&gt;');
                statusCell.innerHTML = xhr.status;

                firstRow.appendChild(responseHeadersCell);
                secondRow.appendChild(responseCell);
                thirdRow.appendChild(statusCell);

                tableHolder.appendChild(firstRow);
                tableHolder.appendChild(secondRow);
                tableHolder.appendChild(thirdRow);

                document.body.appendChild(tableHolder);
            }
        };

        xhr.open(method, url, true);
        xhr.withCredentials = true;
        xhr.setRequestHeader("Authorization", "Basic " + btoa(username + ":" + password));
        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(dataString);

        // remove listener to clean up
        window.removeEventListener("load", sendingTweetForProcessing);
    });
})();
