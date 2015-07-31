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
var fs= require('fs');
var https = require('https');
var querystring = require('querystring');

var str = ['PREFIX dc: <http://purl.org/dc/elements/1.1/>',
'INSERT DATA { <http://example/egbook> dc:title  "This is an example title" }'].join("\n");

var dataString = querystring.stringify({
    update: str
});
console.log(dataString);
var headers = {
    'Accept': 'application/sparql-results+xml',
    'Content-Type': 'application/rdf+xml'
};

var options = {
    hostname: 'rdf.s4.ontotext.com',
    path: '/<user-id>/<db>/repositories/<repository>/statements',
    method: 'POST',
    auth: '<api-key>:<api-secret>',
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

var POST_DATA = fs.readFileSync('Leipzig.rdf').toString()
console.log(POST_DATA)
req.write(POST_DATA)
console.log(req)

req.end();
