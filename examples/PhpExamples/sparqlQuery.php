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
$pipeLineUrl = 'https://lod.s4.ontotext.com/v1/FactForge/sparql';



$data = "query=" . urlencode('
 PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX dbpedia: <http://dbpedia.org/resource/>
PREFIX dbp-ont: <http://dbpedia.org/ontology/>
PREFIX geo-ont: <http://www.geonames.org/ontology#>
PREFIX umbel-sc: <http://umbel.org/umbel/sc/>
SELECT DISTINCT ?Company ?Location WHERE {
?Company rdf:type dbp-ont:Company ;
dbp-ont:industry dbpedia:Computer_software ;
dbp-ont:foundationPlace ?Location .
?Location geo-ont:parentFeature ?o.
?o geo-ont:parentCountry dbpedia:United_States . } limit 5
');

$options = array(
    'http' => array(
        'header' => "Accept: application/sparql-results+xml\r\n" .
            "Content-type: application/x-www-form-urlencoded\r\n" .
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