<?php
error_reporting(E_ALL);

$username = '<your-credentials-here>';
$password = '<your-credentials-here>';
$pipeLineUrl = 'https://text.s4.ontotext.com/v1/twitie';

$isResultGzipEncoded = false;

$inputStr = <<<DEMO
{"text":"Nearly 200,000 people have been killed in #Syria since the start of the conflict in 2011, according to the U.N. http://t.co/pK7t8AD7Xf","lang":"en",
"entities":{"symbols":[],"urls":[{"expanded_url":"http://on.wsj.com/1pZmkY9","indices":[112,134],"display_url":"on.wsj.com/1pZmkY9","url":"http://t.co/pK7t8AD7Xf"}],
"hashtags":[{"text":"Syria","indices":[42,48]}],"user_mentions":[]},"id":502743846716207104,"created_at":"Fri Aug 22 09:07:28 +0000 2014","id_str":"502743846716207104"}
DEMO;

$data = array(
    "document" => str_replace(array("\r", "\n"), '', $inputStr),
    "documentType" => "text/x-json-twitter",
    "annotationSelectors" => array(":", "Original markups:")
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