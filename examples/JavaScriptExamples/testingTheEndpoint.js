/*
 S4 Web Page Annotation Browser Plugin
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
    window.addEventListener("load", function testEndPointDemo() {
        var username = "<key>";
        var password = "<password>";
        var url = "https://text.s4.ontotext.com/v1/";
        var method = "GET";

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
                responseCell.innerHTML = xhr.responseText;
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
        xhr.send(null);

        // remove listener to clean up
        window.removeEventListener("load", testEndPointDemo);
    });
})();
