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

var https = require('https');

var options = {
    hostname: 'text.s4.ontotext.com',
    path: '/v1/',
    method: 'GET',
    auth: '<keyId>:<password>'
};

var req = https.request(options, function (res) {
    console.log("statusCode: ", res.statusCode);
    console.log("headers: ", res.headers);

    res.on('data', function (d) {
        process.stdout.write(d);
    });
});
req.end();

req.on('error', function (e) {
    console.error(e);
});
