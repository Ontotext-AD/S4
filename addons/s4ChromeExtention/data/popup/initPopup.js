/* Self-Service Semantic Suite
Copyright (c) 2014, Ontotext AD, All rights reserved.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3.0 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library.
*/

(function() {
	var popup = document.querySelector("#s4PopUp");
	if (!popup) {
		var iframe = document.createElement("iframe");
		iframe.setAttribute("style", "width: 100%; height: 100%; border: 0; overflow: hidden;");
		iframe.setAttribute("src", chrome.extension.getURL("data/templates/popup.html"));
		
		popup = document.createElement("div");
		popup.setAttribute("style", "display: none; position: fixed; top: 10px; right: 10px; width: 600px; height: 410px; padding: 5px; background: #fff; border: 1px solid #000; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.4); z-index: 1000000;");
		popup.setAttribute("id", "s4PopUp");
		popup.appendChild(iframe);
		document.body.appendChild(popup);
	}
})();
