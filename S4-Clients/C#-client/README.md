S4 C# client library
====================

A C# client library for the [S4 REST API][1].

## Installation
### NuGet Package Manager
`PM> Install-Package Ontotext.S4.ClientAPI`
### Free
Alternatively, you can download the source code from our [GitHub](https://github.com/Ontotext-AD/S4/tree/master/S4-Clients/C%23-client) page.

## Example Usage

```cs
S4AnnotationClient annoClient = new S4AnnotationClientImpl(ServicesCatalog.getItem("news"), "<api-key>", "<key-secret>");
AnnotatedDocument doc = annoClient.annotateDocument("Your text here", SupportedMimeType.PLAINTEXT);
Console.WriteLine(doc.getEntities());
Console.WriteLine(doc.getText());
Console.WriteLine(doc.getOtherFeatures());
```

## Documentation

Documentation can be found on [the S4 wiki][2]:
- [Client library User Guide][3] 

  [1]: http://docs.s4.ontotext.com/display/S4docs/REST+APIs
  [2]: http://docs.s4.ontotext.com/display/S4docs/
  [3]: http://docs.s4.ontotext.com/display/S4docs/C+Sharp+SDK