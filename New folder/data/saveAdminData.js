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

(function (module) {

    // every request will have unique Id
    var buildEventId = (function (eventId) {
        return function () {
            return "set" + (++eventId);
        }
    })(0);

    var saveAdminData = function ($q) {
        return function (adminObject) {
            var deferred = $q.defer();
            var inputObj = {};
            inputObj['data'] = adminObject;
            inputObj['eventId'] = buildEventId();

            function receiveEmitFromMainFile(emitedObj) {
                if (typeof emitedObj['eventId'] != 'undefined' && emitedObj['eventId'] === inputObj['eventId']) {
                    deferred.resolve(emitedObj['data']);
                    self.port.removeListener("sendAdminObj", receiveEmitFromMainFile);
                }
            }

            self.port.on("sendAdminObj", receiveEmitFromMainFile);

            self.port.emit("setAdminObj", inputObj);

            return deferred.promise;
        }
    };

    module.factory("saveAdminData",
        ["$q", saveAdminData]);

}(angular.module("annoMarketApp")));