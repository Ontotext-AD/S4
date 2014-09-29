(function () {
    window.addEventListener("load", function testEndPointDemo() {
        var username = "<key>";
        var password = "<password>";
        var url = "https://text.s4.ontotext.com/v1/twitie";
        var method = "POST";

        var postData = {
            "documentUrl": "http://www.bbc.com/future/story/20130630-super-shrinking-the-city-car",
            "documentType": "text/html"
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
        window.removeEventListener("load", testEndPointDemo);
    });
})();
