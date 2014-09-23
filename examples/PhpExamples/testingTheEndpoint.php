<?php
error_reporting(E_ALL);

$username = '<your-credentials-here>';
$password = '<your-credentials-here>';
$pipeLineUrl = 'https://text.s4.ontotext.com/v1/';

$isResultGzipEncoded = false;

$options = array(
    'http' => array(
        'header'  => "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" .
                     "Accept-Encoding: gzip, deflate\r\n" .
                     "Accept-Language: en-US,en;q=0.5\r\n".
                     "Authorization: Basic " . base64_encode("$username:$password"),
        'method'  => 'GET'
    )
);

$context  = stream_context_create($options);
$result = file_get_contents($pipeLineUrl, false, $context);


if(isset($http_response_header)) {
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