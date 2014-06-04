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

/**
 *  example of usage:
 *     sendRequestViaSelfPortEmit({
 *       url: 'https://annomarket.com/api/shop',
 *       headers: {
 *           Authorization :  "Basic " + (Base64.encode("username:password")),
 *           Accept: "application/json"
 *       },
 *       methodToRequest: 'get'
 *         }).then(
 *        function (response) {
 *           console.log(response.toSource());
 *       }
 *      );
 */
(function (module) {

    // every request will have unique Id
    var buildRequesId = (function (requestId) {
        return function () {
            return "req" + (++requestId);
        }
    })(0);

    var sendRequestViaSelfPortEmit = function ($q) {
        return function (configObject) {
            var deferred = $q.defer();

            configObject['requestId'] = buildRequesId();

            function receiveHttpRequestToUrlHandler(response) {
                if (typeof response['requestId'] != 'undefined' && response['requestId'] === configObject['requestId']) {
                    deferred.resolve(response['data']);
                    self.port.removeListener("receiveHttpRequestToUrl", receiveHttpRequestToUrlHandler);
                }
            }

            self.port.on("receiveHttpRequestToUrl", receiveHttpRequestToUrlHandler);

            self.port.emit("sendHttpRequestToUrl", configObject);

            return deferred.promise;
        }
    };

    module.factory("sendRequestViaSelfPortEmit",
        ["$q", sendRequestViaSelfPortEmit]);

}(angular.module("annoMarketApp")));