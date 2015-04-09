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

 var togglePopup = function(needToShow) {
	chrome.tabs.executeScript(null, {file: "data/popup/initPopup.js"}); //init if it not exist
	
	if (needToShow === false) {
		chrome.tabs.executeScript(null, {file: "data/popup/hidePopup.js"}); //hide if needed
	} else if (needToShow === true) {
		chrome.tabs.executeScript(null, {file: "data/popup/showPopup.js"}); //show if needed
	} else {
		chrome.tabs.executeScript(null, {file: "data/popup/togglePopup.js"}); //toggle if there are no params
	}
 };
 
chrome.storage.local.set({'S4AdminData': {}}, function() { });

chrome.browserAction.onClicked.addListener(function(tab) {
	togglePopup();
});

chrome.contextMenus.create({
    "title": "Annotate Selection with S4",
    "contexts": ["selection"],
    "onclick" : function(e) {

	chrome.storage.local.get(function (result) {
		var selectionObj = {
			selectedText: e.selectionText
		};
		
		if (!(typeof result.S4AdminData['username'] != 'undefined' &&
			result.S4AdminData['username'] != '' &&
			typeof result.S4AdminData['password'] != 'undefined' &&
			result.S4AdminData['password'] != '' &&
			typeof result.S4AdminData['selectedPipeLine'] != 'undefined' &&
			result.S4AdminData['selectedPipeLine'] != '')) {
			
			selectionObj.templateToRender = './settingsForm.html';
		}
		
		chrome.storage.local.set({'S4Selection': selectionObj}, function() { //Needed for first-time popup generation after context menu click
			togglePopup(true);
			chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
				chrome.tabs.sendMessage(tabs[0].id, {selection: selectionObj}, function(response) {	});
			});
		});
	});

	}
});

chrome.runtime.onMessage.addListener(
  function(request, sender, sendResponse) {
    if (request.newTabUrl) {
		chrome.tabs.create({
			url: request.newTabUrl
		});
	} else if (request.closePopup) {
		togglePopup();
	}
  });