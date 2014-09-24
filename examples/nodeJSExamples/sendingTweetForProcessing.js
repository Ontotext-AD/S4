var https = require('https');

var inputString = ['{"text":"Nearly 200,000 people have been killed in #Syria since the start of the conflict in 2011, according to the U.N. http://t.co/pK7t8AD7Xf","lang":"en",',
    '"entities":{"symbols":[],"urls":[{"expanded_url":"http://on.wsj.com/1pZmkY9","indices":[112,134],"display_url":"on.wsj.com/1pZmkY9","url":"http://t.co/pK7t8AD7Xf"}],',
    '"hashtags":[{"text":"Syria","indices":[42,48]}],"user_mentions":[]},"id":502743846716207104,"created_at":"Fri Aug 22 09:07:28 +0000 2014","id_str":"502743846716207104"}'].join("");

var postData = {
    "document": inputString,
    "documentType": "text/x-json-twitter",
    "annotationSelectors": [":", "Original markups:"]
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
    auth: "<keyId>:<password>",
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
