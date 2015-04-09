/**
 S4 Web Page Annotation Browser Plugin
 Copyright (c) 2013, 2014, Ontotext AD
 
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

// Define an angular module for our app
var s4App = angular.module('s4App', []);

s4App.controller('DefaultController', function ($scope, settings, $http) {
    $scope.templateToRender = './settingsForm.html';
	
	$http.get('https://console.s4.ontotext.com/api/shop/pipelines')
        .then(function(res){
			$scope.pipeLinesList = res.data;
				$scope.showCurrentPageForm = true;                
        });
		
	chrome.storage.local.get(function (result) {
		$scope.adminData = result.S4AdminData;
		if (result.S4Selection && result.S4Selection['selectedText']) { //if text was already selected
			$scope.showInterface(result.S4Selection);
		}
	});
		    
    $scope.setBackgroundColor = function (obj) {
        return { 'background-color': obj.color, 'color': obj.invertColor};
    };

    $scope.closePanel = function () {
        chrome.runtime.sendMessage({closePopup: true}, function(response) { });
    };

    $scope.pipeLinesFormHandler = function () {
        $scope.sendCurrentPageToPipeLine();
    };
	
    $scope.cancelMethod = function() {
        $scope.templateToRender = './settingsForm.html';
    }

    $scope.goToSettingsForm = function() {
        $scope.templateToRender = './settingsForm.html';
    }

    $scope.s4FormConfigurationFormSubmitHandler = function () {
        $scope.templateToRender = './confirmSave.html';
    };

    $scope.sendCurrentPageToPipeLine = function() {
        $scope.templateToRender = './loader.html';
		
		if (typeof  $scope.adminData.selectedPipeLine["url"] != 'undefined') {
			var requestData = JSON.stringify({
				"document": $scope.selectedTextFromUser,
				"annotationSelectors": [],
				"documentType": "text/plain"
			});

			var headers = {
				"Accept": "application/gate+json",
				//"Accept-Encoding": "gzip, deflate",
				"Content-Type": "application/json;charset=utf-8"
			};

			if (typeof $scope.adminData.username != 'undefined' &&
				typeof $scope.adminData.password != 'undefined' &&
				$scope.adminData.username != "" &&
				$scope.adminData.password != "") {
				headers['Authorization'] = "Basic " + btoa($scope.adminData.username + ":" + $scope.adminData.password);
			}

			/**
			 * The request must be send to
			 * $scope.adminData.selectedPipeLine["url"]
			*/
			 
			 $http({
				url: $scope.adminData.selectedPipeLine["url"],
				method: "POST",
				data: requestData,
				headers: headers
			}).success(function (data, status, headers, config) {
				/**
                 * Error handling
                */
                if (typeof data.message != 'undefined') {
                    $scope.showError(data.message);
                    return;
                }

                $scope.showS4Data(data);
			}).error(function(data, status, headers, config) {
				var errorMessage = "The system returns status: " + status;

				$scope.showError(errorMessage);
			});
		}
    };

    $scope.confirmSaveCredentials = function() {
	    chrome.storage.local.set({'S4AdminData': $scope.adminData}, function() { });
        
		if(angular.isDefined($scope.autoSendRequestForm)) {
			delete $scope.autoSendRequestForm;
			$scope.sendCurrentPageToPipeLine();
		} else if(typeof $scope.adminData['username'] != 'undefined' &&
			$scope.adminData['username'] != '' &&
			typeof $scope.adminData['password'] != 'undefined' &&
			$scope.adminData['password'] != '' &&
			typeof $scope.adminData['selectedPipeLine'] != 'undefined' &&
			$scope.adminData['selectedPipeLine'] != '') {
			$scope.templateToRender = './settingsForm.html';
			$scope.closePanel();
		} else {
			$scope.templateToRender = './settingsForm.html';
		}
    }

    $scope.cancelSaveEmptyCredentials = function() {
        $scope.templateToRender = './settingsForm.html';
    }

    $scope.cancelSaveCredentials = function () {
		chrome.storage.local.get(function (result) {
			$scope.$apply(function () {
				$scope.adminData = result.S4PopUpModel;
				$scope.templateToRender = './settingsForm.html';
			});
		});
    }

    $scope.goToInfoPage = function() {
        $scope.templateToRender = './info.html';
    }

    $scope.gotoS4Page = function() {
		chrome.runtime.sendMessage({newTabUrl: "https://console.s4.ontotext.com/"}, function(response) { });
    }
	
	$scope.showError = function (error) {
		$scope.errorMessage = error;
		$scope.templateToRender = './error.html';
	}

	$scope.showInterface = function (inputData) {
        /**
         * user is selected text ..
         */
		chrome.storage.local.set({'S4Selection': {}}, function() { });
		if(angular.isDefined(inputData) && angular.isDefined(inputData['selectedText']) && angular.isDefined(inputData['templateToRender']) == false) {
			$scope.$apply(function () {
                $scope.selectedTextFromUser = inputData['selectedText'];
                $scope.sendCurrentPageToPipeLine();
            });
		} else if(angular.isDefined(inputData) && angular.isDefined(inputData['selectedText']) && angular.isDefined(inputData['templateToRender']) == true) {
            $scope.$apply(function () {
                $scope.autoSendRequestForm = true;
                $scope.selectedTextFromUser = inputData['selectedText'];
                $scope.templateToRender = inputData['templateToRender'];
            });
        } else if(angular.isDefined(inputData) && angular.isDefined(inputData['selectedText']) == false && angular.isDefined(inputData['templateToRender']) == true){
            $scope.$apply(function () {
                $scope.templateToRender = inputData['templateToRender'];
            });
        } else if(typeof inputData == 'undefined' &&
                  $scope.templateToRender != './result-page.html' &&
                  angular.isDefined(inputData) == false &&
                  angular.isDefined(inputData['templateToRender']) == false) {
            $scope.$apply(function () {
                $scope.templateToRender = './settingsForm.html';
            });
        }
    };
    
    $scope.showS4Data = function (inputData) {
        /**
         * here we must set default not displayed
         * annotation
         */
        for (var i in inputData['entities']) {
            if (inputData['entities'].hasOwnProperty(i) && settings.typesToFilter.indexOf(i) != -1) {
                inputData['entities'][i][0]['s4HideItem'] = true;
            }
        }

		$scope.resultFromResponse = inputData;
		$scope.templateToRender = './result-page.html';
    };

    $scope.removeS4Type = function(typeItem) {
        if(angular.isDefined($scope.resultFromResponse['entities'][typeItem][0])) {
            $scope.resultFromResponse['entities'][typeItem][0]['s4HideItem'] = true;
            var changedItems = angular.copy($scope.resultFromResponse);
            delete $scope.resultFromResponse;
            $scope.resultFromResponse = changedItems;
        }
    }

    $scope.showS4Type = function(typeItem) {
        if(angular.isDefined($scope.resultFromResponse['entities'][typeItem][0]) == true && angular.isDefined($scope.resultFromResponse['entities'][typeItem][0]['s4HideItem']) == true ) {
            var changedItems = angular.copy($scope.resultFromResponse);
            delete changedItems['entities'][typeItem][0]['s4HideItem'];
            delete $scope.resultFromResponse;
            $scope.resultFromResponse = changedItems;
        }
    }	
	
	chrome.runtime.onMessage.addListener(
		function(request, sender, sendResponse) {
			if (request.selection != null)
				$scope.showInterface(request.selection);
	});
});