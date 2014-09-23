<?php
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