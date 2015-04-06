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

var buttons = require('sdk/ui/button/action');
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

var button = buttons.ActionButton({
    id: "s4-interface",
    label: "S4 interface",
    icon: self.data.url("s4Logo.png"),
    onClick: function (view) {
		s4Popup.show({
            position: button
        });
    }
});

var menuItem = contextMenu.Item({
    label: "Annotate Selection with S4",
    context: contextMenu.SelectionContext(),
    contentScript: 'self.on("click", function () {' +
        '  var text = window.getSelection().toString().replace(/(?:\\r\\n|\\n|\\r|\\s)/gm," ");' +
        '  self.postMessage(text);' +
        '});',
    onMessage: function (selectionText) {
        selectedText = selectionText;

        if (typeof ss.storage.adminObj['username'] != 'undefined' &&
            ss.storage.adminObj['username'] != '' &&
            typeof ss.storage.adminObj['password'] != 'undefined' &&
            ss.storage.adminObj['password'] != '' &&
            typeof ss.storage.adminObj['selectedPipeLine'] != 'undefined' &&
            ss.storage.adminObj['selectedPipeLine'] != '') {
            s4Popup.port.emit("showInterface", {
                selectedText: selectedText
            });
        } else {
            s4Popup.port.emit("showInterface", {
                selectedText: selectedText,
                templateToRender: './settingsForm.html'
            });
        }

        s4Popup.show({
            position: button
        });
    }
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
                if (typeof response.json != 'undefined' && response.json != null && typeof response.json.message != 'undefined') {
                    s4Popup.port.emit("error", response.json.message);
                    return;
                } else if (typeof response.json == 'undefined' || response.json == null) {
                    var errorMessage = "The system returns status: " + response.status;

                    if (typeof response.statusText != 'undefined') {
                        errorMessage += " message: ";
                        errorMessage += response.statusText;
                    }

                    s4Popup.port.emit("error", errorMessage);
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