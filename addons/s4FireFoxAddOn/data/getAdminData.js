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

/**
 * Service to get configuration data
 */
s4App.service('getAdminData', ["$q", function($q){

    // every request will have unique Id
    var buildEventId = (function (eventId) {
        return function () {
            return "get" + (++eventId);
        }
    })(0);

    var getAdminData = function() {
        var configObj = {};
        var deferred = $q.defer();

        configObj['eventId'] = buildEventId();

        function receiveEmitFromMainFile(emitedObj) {
            if (typeof emitedObj['eventId'] != 'undefined' && emitedObj['eventId'] === configObj['eventId']) {
                deferred.resolve(emitedObj['data']);
                self.port.removeListener("sendAdminObj", receiveEmitFromMainFile);
            }
        }

        self.port.on("sendAdminObj", receiveEmitFromMainFile);
        self.port.emit("getAdminObj", configObj);

        return deferred.promise;
    };

    return getAdminData;
}]);