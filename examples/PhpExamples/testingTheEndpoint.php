<?php
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