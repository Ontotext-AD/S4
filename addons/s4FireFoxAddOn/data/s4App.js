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

s4App.controller('DefaultController', function ($scope, sendRequestViaSelfPortEmit, sendCurrentPageToPipeLine, getAdminData, saveAdminData, settings) {

    $scope.templateToRender = './choosePipeLine.html';

    $scope.credentialsIsSet = false;

    sendRequestViaSelfPortEmit({
        methodToRequest: 'get',
        url: 'http://console.s4.ontotext.com/api/shop/pipelines',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Accept-Encoding': 'gzip, deflate',
            'Accept-Language': 'en-US,en;q=0.5',
            'Host': 'console.s4.ontotext.com',
            'Referer': 'http://console.s4.ontotext.com/'
        }
    }).then(
        function (response) {
            $scope.pipeLinesList = response;
            $scope.showCurrentPageForm = true;
        }
    );

    getAdminData().then(function(adminData) {
        $scope.adminData = adminData;
    });

    $scope.$watch('adminData', function () {
        if (typeof $scope.adminData != 'undefined' &&
            typeof $scope.adminData.username != 'undefined' &&
            typeof $scope.adminData.password != 'undefined') {
            $scope.credentialsIsSet = true;
        } else {
            $scope.credentialsIsSet = false;
        }
    });

    $scope.setBackgroundColor = function (obj) {
        return { 'background-color': obj.color, 'color': obj.invertColor};
    };

    $scope.closePanel = function () {
        self.port.emit('hidePanel');
    };

    $scope.pipeLinesFormHandler = function () {
        $scope.sendCurrentPageToPipeLine();
    };

    $scope.sendCurrentWorkingPageToPipeline = function () {
        self.port.emit("getAdminObj", {eventId: 1});
    };

    $scope.cancelMethod = function() {
        $scope.templateToRender = './choosePipeLine.html';
    }

    $scope.goToSettingsForm = function() {
        $scope.templateToRender = './settingsForm.html';
    }

    $scope.s4FormConfigurationFormSubmitHandler = function () {
        $scope.templateToRender = './confirmSave.html';
    };

    $scope.sendCurrentPageToPipeLine = function() {
        $scope.templateToRender = './loader.html';
        sendCurrentPageToPipeLine($scope.selectedPipeLine,$scope.selectedTextFromUser).then(
          function(result) {
              $scope.colorLegend = result;
              $scope.templateToRender = './legend.html';
          }
        );
    };

    $scope.deleteCredentials = function() {
        saveAdminData({}).then(
            function (adminData) {
				if(angular.isDefined($scope.selectedPipeLine)) {
					delete $scope.selectedPipeLine;
				}
				
                $scope.adminData = adminData;
                $scope.templateToRender = './choosePipeLine.html';
            }
        );
    };

    $scope.confirmSaveCredentials = function() {
        saveAdminData($scope.adminData).then(
            function (adminData) {
                $scope.adminData = adminData;
                $scope.templateToRender = './choosePipeLine.html';
            }
        );
    }

    $scope.cancelSaveEmptyCredentials = function() {
       $scope.templateToRender = './settingsForm.html';
    }

    $scope.cancelSaveCredentials = function () {
        getAdminData().then(function(adminData) {
            $scope.adminData = adminData;
            $scope.templateToRender = './settingsForm.html';
        });
    }

    $scope.goToInfoPage = function() {
        $scope.templateToRender = './info.html';
    }

    $scope.gotoS4Page = function() {
        self.port.emit("gotoS4Page");
    }

    self.port.on('error', function (error) {
        $scope.$apply(function () {
            $scope.errorMessage = error;
            $scope.templateToRender = './error.html';
        });
    });

    self.port.on('showInterface', function (inputData) {
		if(angular.isDefined(inputData['selectedText'])) {
			$scope.$apply(function () {
				$scope.templateToRender = './choosePipeLine.html';
                $scope.selectedTextFromUser = inputData['selectedText'];
            });
		}
    });

    self.port.on('showStartPage', function (inputData) {
		$scope.$apply(function () {
			$scope.templateToRender = './choosePipeLine.html';
		});
    });
    
    self.port.on('s4Message', function (inputData) {
        /**
         * here we must set default not displayed
         * annotation
         */
        for (var i in inputData['data']['entities']) {
            if (inputData['data']['entities'].hasOwnProperty(i) && settings.typesToFilter.indexOf(i) != -1) {
                inputData['data']['entities'][i][0]['s4HideItem'] = true;
            }
        }

        $scope.$apply(function () {
            $scope.resultFromResponse = inputData['data'];
            $scope.templateToRender = './result-page.html';
        });
    });

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
});
