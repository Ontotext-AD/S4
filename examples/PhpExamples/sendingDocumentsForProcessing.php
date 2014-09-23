<?php
error_reporting(E_ALL);

$username = '<your-credentials-here>';
$password = '<your-credentials-here>';
$pipeLineUrl = 'https://text.s4.ontotext.com/v1/twitie';

$isResultGzipEncoded = false;

$inputStr = <<<DEMO
London
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