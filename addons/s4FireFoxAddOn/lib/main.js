var widgets = require("sdk/widget");
var self = require("sdk/self");
var windows = require("sdk/windows").browserWindows;
var ss = require("sdk/simple-storage");
var Base64 = require("sdk/base64");
var Request = require("sdk/request").Request;
var tabs = require("sdk/tabs");
var contextMenu = require("sdk/context-menu");
var selectedText = '';


if (!ss.storage.adminObj) {
    /**
     * On first addon starting
     * we will ask from user to set
     * username and password.
     */
    ss.storage.adminObj = {};
}

var s4Popup = require("sdk/panel").Panel({
    width: 570,
    height: 400,
    contentURL: self.data.url("./templates/popup.html"),
    contentScriptFile: [
        self.data.url("jquery-1.11.1.min.js"),
        self.data.url("angular.min.js"),
        self.data.url("s4App.js"),
        self.data.url("constants.js"),
        self.data.url("stringHexNumber.js"),
        self.data.url("createHighlightedArea.js"),       
        self.data.url("directives.js"),
        self.data.url("sendRequestViaSelfPortEmit.js"),
        self.data.url("saveAdminData.js"),
        self.data.url("getAdminData.js"),
        self.data.url("sendCurrentPageToPipeLine.js")
    ]
});

var menuItem = contextMenu.Item({
    label: "Annotate Selection",
    context: contextMenu.SelectionContext(),
    contentScript: 'self.on("click", function () {' +
        '  var text = window.getSelection().toString().replace(/(?:\\r\\n|\\n|\\r|\\s)/gm," ");' +
        '  self.postMessage(text);' +
        '});',
    onMessage: function (selectionText) {
        selectedText = selectionText;
        s4Popup.port.emit("showInterface", {
            data: tabs.activeTab.url,
            selectedText: selectedText
        });
        
        s4Popup.show({
            position: {
                right: 0,
                top: 0
            }
        });
    }
});

var widget = widgets.Widget({
    id: "s4-interface",
    label: "S4 interface",
    contentURL: self.data.url("s4Logo.png"),
    onClick: function (view) {
        view.panel = s4Popup;
    }
});

s4Popup.on("show", function() {
    s4Popup.port.emit("showInterface", {
        data: tabs.activeTab.url
    });
});

s4Popup.port.on("getAdminObj", function (receivedObj) {
    if (typeof receivedObj['eventId'] != 'undefined') {
        s4Popup.port.emit("sendAdminObj", {
            eventId: receivedObj['eventId'],
            data: ss.storage.adminObj
        });
    }
});


s4Popup.port.on("hidePanel", function (adminObj) {
    s4Popup.hide();
});


s4Popup.port.on("setAdminObj", function (adminObj) {
    if (typeof adminObj['eventId'] != 'undefined') {
        ss.storage.adminObj = adminObj['data'];
        s4Popup.port.emit("sendAdminObj", adminObj);
    }
});

s4Popup.port.on("sendHttpRequestToUrl", function (configObj) {
    if (["get", "post"].indexOf(configObj['methodToRequest']) != -1) {
        var request = Request({
            url: configObj['url'],
            headers: configObj['headers'],
            onComplete: function (response) {
                s4Popup.port.emit("receiveHttpRequestToUrl", {
                    requestId: configObj['requestId'],
                    data: response.json
                });
            }
        });
        request[configObj['methodToRequest']]();
    }
});

s4Popup.port.on("sendCurrentPageToPipeLine", function (eventInputs) {
	
    if (typeof eventInputs['eventId'] != 'undefined' && typeof  eventInputs['data']["url"] != 'undefined') {
		        var requestData = JSON.stringify({
                    "document": eventInputs['selectedTextFromUser'],
                    "annotationSelectors": [],
                    "documentType": "text/plain"
                });

                var headers = {
                    "Accept": "application/gate+json",
                    "Accept-Encoding": "gzip, deflate",
                    "Content-Type": "application/json;charset=utf-8"
                };
                
                if (typeof ss.storage.adminObj.username != 'undefined' &&
                    typeof ss.storage.adminObj.password != 'undefined' &&
                    ss.storage.adminObj.username != "" &&
                    ss.storage.adminObj.password != "") {
                    headers['Authorization'] = "Basic " + Base64.encode(ss.storage.adminObj.username + ":" + ss.storage.adminObj.password);
                }

                /**
                 * The request must be send to
                 * eventInputs['data']["url"]
                 */
                Request({
                    url: eventInputs['data']["url"],
                    content: requestData,
                    headers: headers,
                    onComplete: function (response) {
                        /**
                         * Error handling
                         */
                        if(typeof response.json != 'undefined' && response.json != null && typeof response.json.message != 'undefined') {
                            s4Popup.port.emit("error", response.json.message);
                            return;
                        } else if(typeof response.json == 'undefined' ||  response.json == null) {
                            s4Popup.port.emit("error", "The system returns status:" + response.status);
                            return;
                        }
                        
						s4Popup.port.emit("s4Message", {
							data: response.json
						});
                    }
                }).post();
    }
});

s4Popup.port.on("gotoS4Page", function () {
    tabs.open({
        url: "https://console.s4.ontotext.com/",
        inBackground: false
    });
});
