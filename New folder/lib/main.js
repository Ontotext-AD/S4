var widgets = require("sdk/widget");
var self = require("sdk/self");
var windows = require("sdk/windows").browserWindows;
var ss = require("sdk/simple-storage");
var Base64 = require("sdk/base64");
var Request = require("sdk/request").Request;
var tabs = require("sdk/tabs");

if (!ss.storage.adminObj) {
    /**
     * On first addon starting
     * we will ask from user to set
     * username and password.
     */
    ss.storage.adminObj = {};
}


var annoMarketPopup = require("sdk/panel").Panel({
    width: 570,
    height: 400,
    contentURL: self.data.url("./templates/popup.html"),
    contentScriptFile: [
        self.data.url("angular.min.js"),
        self.data.url("annoMarketApp.js"),
        self.data.url("sendRequestViaSelfPortEmit.js"),
        self.data.url("saveAdminData.js"),
        self.data.url("getAdminData.js"),
        self.data.url("sendCurrentPageToPipeLine.js")
    ]
});

var widget = widgets.Widget({
    id: "anno-market-interface",
    label: "Anno market interface",
    contentURL: self.data.url("annoMarketIcon.png"),
    onClick: function (view) {
        view.panel = annoMarketPopup;
    }
});


annoMarketPopup.port.on("fileIsSelected", function () {
    widget.getView(windows.activeWindow).panel.show({
        position: {
            right: 0,
            bottom: 0
        }
    });
});

annoMarketPopup.on("show", function() {
    annoMarketPopup.port.emit("showInterface", {
        data: tabs.activeTab.url,
        resultPageUrl: self.data.url("templates/result-page.html")
    });
});

annoMarketPopup.port.on("getAdminObj", function (receivedObj) {
    if (typeof receivedObj['eventId'] != 'undefined') {
        annoMarketPopup.port.emit("sendAdminObj", {
            eventId: receivedObj['eventId'],
            data: ss.storage.adminObj
        });
    }
});


annoMarketPopup.port.on("hidePanel", function (adminObj) {
    annoMarketPopup.hide();
});


annoMarketPopup.port.on("setAdminObj", function (adminObj) {
    if (typeof adminObj['eventId'] != 'undefined') {
        ss.storage.adminObj = adminObj['data'];
        annoMarketPopup.port.emit("sendAdminObj", adminObj);
    }
});

annoMarketPopup.port.on("sendHttpRequestToUrl", function (configObj) {
    if (["get", "post"].indexOf(configObj['methodToRequest']) != -1) {
        var request = Request({
            url: configObj['url'],
            headers: configObj['headers'],
            onComplete: function (response) {
                annoMarketPopup.port.emit("receiveHttpRequestToUrl", {
                    requestId: configObj['requestId'],
                    data: response.json
                });
            }
        });
        request[configObj['methodToRequest']]();
    } else {
        annoMarketPopup.port.emit("receiveHttpRequestToUrl", {
            requestId: configObj['requestId'],
            data: JSON.parse(self.data.load("shop.json"))
        });
    }
});

annoMarketPopup.port.on("sendCurrentPageToPipeLine", function (eventInputs) {
    if (typeof eventInputs['eventId'] != 'undefined' && typeof  eventInputs['data']["onlineUrl"] != 'undefined') {
        Request({
            url: tabs.activeTab.url,
            onComplete: function (response) {
                var requestData = JSON.stringify({
                    "document": response.text,
                    "mimeType": "text/html",
                    "annotationSelectors": []
                });

                var headers = {
                    Accept: "application/json",
                    "Content-Type": "application/json"
                };

                if (typeof ss.storage.adminObj.username != 'undefined' &&
                    typeof ss.storage.adminObj.password != 'undefined' &&
                    ss.storage.adminObj.username != "" &&
                    ss.storage.adminObj.password != "") {
                    headers['Authorization'] = "Basic " + Base64.encode(ss.storage.adminObj.username + ":" + ss.storage.adminObj.password);
                }

                /**
                 * The request must be send to
                 * eventInputs['data']["onlineUrl"]
                 */
                Request({
                    url: eventInputs['data']["onlineUrl"],
                    content: requestData,
                    headers: headers,
                    onComplete: function (response) {

                        /**
                         * Error handling
                         */
                        if(typeof response.json != 'undefined' && response.json != null && typeof response.json.message != 'undefined') {
                            annoMarketPopup.port.emit("error", response.json.message);
                            return;
                        } else if(typeof response.json == 'undefined' ||  response.json == null) {
                            annoMarketPopup.port.emit("error", "The system returns status:" + response.status);
                            return;
                        }

                        for each (var tab in tabs) {
                            if(tab.url == self.data.url("templates/result-page.html")) {
                                tab.close();
                            }
                        }

                        /**
                         * Here we will open new tab.
                         */
                        tabs.open({
                            url: self.data.url("./templates/result-page.html"),
                            inBackground: false,
                            onReady: function (tab) {
                                worker = tab.attach({
                                    contentScriptFile: self.data.url("highlighter.js"),
                                    onMessage: function (message) {
                                        annoMarketPopup.port.emit("getRequestResult", {
                                            eventId: eventInputs['eventId'],
                                            data: message
                                        });
                                    }
                                });
                                worker.port.emit("annoMarketMessage", response.json);
                            }
                        });
                    }
                }).post();
            }
        }).get();
    }
});

annoMarketPopup.port.on("gotoAnnoMarketPage", function () {
    tabs.open({
        url: "http://annomarket.com/youraccount/apikeys",
        inBackground: false
    });
});
