/**
 AnnoMarket Web Page Annotation Browser Plugin
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
var annoMarketApp = angular.module('annoMarketApp', []);


annoMarketApp.controller('DefaultController', function ($scope, sendRequestViaSelfPortEmit, sendCurrentPageToPipeLine, getAdminData, saveAdminData) {

    $scope.templateToRender = './choosePipeLine.html';

    $scope.credentialsIsSet = false;

    sendRequestViaSelfPortEmit({
        methodToRequest: 'local'
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

    $scope.setBackgroundColor = function (color) {
        return { 'background-color': color};
    }

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

    $scope.annoMarketFormConfigurationFormSubmitHandler = function () {
        $scope.templateToRender = './confirmSave.html';
    };

    $scope.sendCurrentPageToPipeLine = function() {
        $scope.templateToRender = './loader.html';
        sendCurrentPageToPipeLine($scope.selectedPipeLine).then(
          function(result) {
              $scope.colorLegend = result;
              $scope.templateToRender = './legend.html';
          }
        );
    };

    $scope.blankSettings = function () {
        $scope.templateToRender = './confirmAnonimous.html';
    };

    $scope.deleteCredentials = function() {
        saveAdminData({}).then(
            function (adminData) {
                $scope.adminData = adminData;
                $scope.templateToRender = './choosePipeLine.html';
            }
        );
    };

    $scope.confirmSaveEmptyCredentials = function() {
        $scope.adminData.username = "";
        $scope.adminData.password = "";

        saveAdminData($scope.adminData).then(
            function (adminData) {
                $scope.adminData = adminData;
                $scope.templateToRender = './choosePipeLine.html';
            }
        );
    }

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

    $scope.gotoAnnoMarketPage = function() {
        self.port.emit("gotoAnnoMarketPage");
    }

    self.port.on('error', function (error) {
        $scope.$apply(function () {
            $scope.errorMessage = error;
            $scope.templateToRender = './error.html';
        });
    });

    self.port.on('showInterface', function (inputData) {
        if(/^http:\/\/|https:\/\//.test(inputData['data'])) {
            $scope.$apply(function () {
                $scope.templateToRender = './choosePipeLine.html';
            });
        } else if(inputData['data'] == inputData['resultPageUrl']) {
            $scope.$apply(function () {
                $scope.templateToRender = './legend.html';
            });
        } else {
            $scope.$apply(function () {
                $scope.templateToRender = './message.html';
            });
        }
    });
});
