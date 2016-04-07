S4 Java client library
=============================

A Java client library and command-line tool for the [S4 REST API][1].

## Command line interface (CLI) usage

```
 S4ClientService parameter1=value1 parameter2=value2 ...
 Parameters:
	service - the service id to be used (one of: 'TwitIE', 'SBT', 'news' and 'news-classifier')
	file	- input file path
	url     - input document URL
	dtype   - the type of the document (one of:'PLAINTEXT', 'HTML', 'XML_APPLICATION', 'XML_TEXT', 'PUBMED', 'COCHRANE', 'MEDIAWIKI', 'TWITTER_JSON')
	out     - result file name. Defaults to 'result.json'
	apikey  - the api key if credentials file is not used
	secret  - the api secret if credentials file is not used
	creds   - credentails file path (if apikey and secret parameters are not used)
```

A sample credentials file is provided as well.

## Documentation

Documentation can be found on [the S4 wiki][2]:
- [Client library User Guide][3] 
- [Client library JavaDocs][4]



  [1]: http://docs.s4.ontotext.com/display/S4docs/REST+APIs
  [2]: http://docs.s4.ontotext.com/display/S4docs/S4+Overview
  [3]: http://docs.s4.ontotext.com/display/S4docs/Java+SDK
  [4]: http://ontotext-ad.github.io/S4/java-client/javadoc/
