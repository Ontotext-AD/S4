/*
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

var inputString = "London";

var postData = {
    "documentUrl": "http://www.bbc.com/future/story/20130630-super-shrinking-the-city-car",
    "documentType": "text/html"
};

var dataString = JSON.stringify(postData);

var headers = {
    'Content-Type': 'application/json',
    'Content-Length': dataString.length
};

var options = {
    hostname: 'text.s4.ontotext.com',
    path: '/v1/twitie',
    method: 'POST',
    auth: '<keyId>:<password>',
    headers: headers
};

// Setup the request.  The options parameter is
// the object we defined above.
var req = https.request(options, function (res) {
    res.setEncoding('utf-8');

    var responseString = '';

    res.on('data', function (data) {
        responseString += data;
    });

    res.on('end', function () {
        console.log("statusCode: ", res.statusCode);
        console.log("headers: ", res.headers);
        process.stdout.write(responseString);
    });
});

req.on('error', function (e) {
    console.log(e);
});

req.write(dataString);
req.end();
