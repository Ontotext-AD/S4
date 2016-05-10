S4 Java client library
======================

A Java client library and a command-line tool for the [S4 REST API][1].

## Installation
### Maven resource
```
<dependency>
  <groupId>com.ontotext.s4</groupId>
  <artifactId>s4-client</artifactId>
  <version>1.1.0</version>
</dependency>
```
### Source download
Alternatively you can download the source code from our [GitHub](https://github.com/Ontotext-AD/S4/tree/master/S4-Clients/Java-client) page

### .jar download
Or you can download the packaged `.jar` from [here](http://ontotext-ad.github.io/S4/java-client/cli/s4-client-1.2.0-jar-with-dependencies.jar).

## Example usage
```java
S4AnnotationClient annoClient = new S4AnnotationClientImpl(ServicesCatalog.getItem("news"), "<api-key>", "<key-secret>");
AnnotatedDocument doc = annoClient.annotateDocument("Your text here", SupportedMimeType.PLAINTEXT);
System.out.println(doc.getEntities());
System.out.println(doc.getText());
System.out.println(doc.getOtherFeatures());
```

## Command line interface (CLI) usage

*Note:* If you have downloaded the source code and built the project, there should be a `s4-client-x.x.x-jar-with-dependencies.jar` in your `target` folder.

Usage: `java -jar S4CommandLineTool.jar [options]`
```
  Options:
  * -k, --api-key                The S4 api key
  * -s, --key-secret             The S4 api secret
  * -t, --service-type           The S4 service ID to be used
  * -d, --document-type          Type of the document to be processed
  * -o, --output-file            Path to file where the service output will be stored
    -f, --file-path              Input file path (somewhere in your local system)
    -u, --url-location           Input URL document
        --img-categorization     Get categories for the images found in the URL document
        --img-tagging            Get tags for the images found in the URL document
    -h, --help                   Display this help text

  * - Required.
```

## Documentation

Documentation can be found on [the S4 wiki][2]:
- [Client library User Guide][3] 
- [Client library JavaDocs][4]

  [1]: http://docs.s4.ontotext.com/display/S4docs/REST+APIs
  [2]: http://docs.s4.ontotext.com/display/S4docs/S4+Overview
  [3]: http://docs.s4.ontotext.com/display/S4docs/Java+SDK
  [4]: http://ontotext-ad.github.io/S4/java-client/javadoc/