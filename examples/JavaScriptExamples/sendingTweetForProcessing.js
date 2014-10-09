/*
 Copyright  2013, 2014, Ontotext AD

 This file is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License along
 with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

(function () {
    window.addEventListener("load", function sendingTweetForProcessing() {
        var username = "<key>";
        var password = "<password>";
        var url = "https://text.s4.ontotext.com/v1/twitie";
        var method = "POST";

        var inputString = ['{"text":"Nearly 200,000 people have been killed in #Syria since the start of the conflict in 2011, according to the U.N. http://t.co/pK7t8AD7Xf","lang":"en",',
            '"entities":{"symbols":[],"urls":[{"expanded_url":"http://on.wsj.com/1pZmkY9","indices":[112,134],"display_url":"on.wsj.com/1pZmkY9","url":"http://t.co/pK7t8AD7Xf"}],',
            '"hashtags":[{"text":"Syria","indices":[42,48]}],"user_mentions":[]},"id":502743846716207104,"created_at":"Fri Aug 22 09:07:28 +0000 2014","id_str":"502743846716207104"}'].join("");

        var postData = {
            "document": inputString,
            "documentType": "text/x-json-twitter",
            "annotationSelectors": [":", "Original markups:"]
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
        xhr.setRequestHeader("Content-Length", dataString.length);
        xhr.send(dataString);

        // remove listener to clean up
        window.removeEventListener("load", sendingTweetForProcessing);
    });
})();
