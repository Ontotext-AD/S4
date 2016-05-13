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
$pipeLineUrl = 'https://text.s4.ontotext.com/v1/news';

$isResultGzipEncoded = false;

$inputStr = <<<DEMO
<your-text-here>
DEMO;

$data = array(
    "document" => $inputStr,
    "documentType" => "text/plain"
);

$options = array(
    'http' => array(
        'header' => "Accept: application/json\r\n" .
            "Content-type: application/json\r\n" .
            "Accept-Encoding: gzip\r\n" .
            "Authorization: Basic " . base64_encode("$username:$password"),
        "content" => json_encode($data),
        'method' => 'POST'
    )
);

$context = stream_context_create($options);
$result = file_get_contents($pipeLineUrl, false, $context);


if (isset($http_response_header)) {
    array_walk($http_response_header, function ($itemValue) {
        global $isResultGzipEncoded;
        /**
         * Here we will detect for gzip encoding.
         */
        if (preg_match("#Content-Encoding:\s+gzip#", $itemValue) == 1) {
            $isResultGzipEncoded = true;
        }

    });
    var_dump($http_response_header);
}

if ($isResultGzipEncoded) {
    var_dump(gzdecode($result));
} else {
    var_dump($result);
}