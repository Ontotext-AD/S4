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
