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