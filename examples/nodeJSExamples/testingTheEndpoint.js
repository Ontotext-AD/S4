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
