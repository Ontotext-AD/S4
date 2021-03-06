<?php
/*
Copyright 2016 Ontotext AD

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

error_reporting(E_ALL);

$username = '<s4-api-key>';
$password = '<s4-key-secret>';
$pipeLineUrl = 'https://rdf.s4.ontotext.com/<user-id>/<db-id>/repositories/<repo-name>/statements';

$update = "PREFIX dc: <http://purl.org/dc/elements/1.1/>
INSERT DATA {<http://example/egbook> dc:title \"This is an example title\"}";


$data = "update=" . $update;

$options = array(
    'http' => array(
        'header' => "Content-Type: application/x-www-form-urlencoded\r\n" .
            "Authorization: Basic " . base64_encode("$username:$password"),
        "content" => $data,
        'method' => 'POST'
    )
);

$context = stream_context_create($options);
$result = file_get_contents($pipeLineUrl, true, $context);


if (isset($http_response_header)) {
    var_dump($http_response_header);
}

var_dump($result);