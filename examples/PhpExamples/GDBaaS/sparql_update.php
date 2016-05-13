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

$username = 's4ubcmbn7csq';
$password = 'dh8escu9q9pm1tc';
$pipeLineUrl = 'https://rdf.s4.ontotext.com/4839327863/TestDataBase/repositories/asd/statements';



$data = "update=" . urlencode('PREFIX dc: <http://purl.org/dc/elements/1.1/>
INSERT DATA {<http://example/egbook> dc:title \"This is an example title\"}');

$options = array(
    'http' => array(
        'header' => 
            "Content-Type: application/x-www-form-urlencoded\r\n" .
            "Authorization: Basic " . base64_encode("$username:$password"),
        "content" => $data,
        'method' => 'POST'
    )
);

$context = stream_context_create($options);
$result = file_get_contents($pipeLineUrl, false, $context);


if (isset($http_response_header)) {
    var_dump($http_response_header);
}

var_dump($result);