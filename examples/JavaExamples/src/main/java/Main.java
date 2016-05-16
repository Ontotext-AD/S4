/*
 * Copyright 2016 Ontotext AD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import service.S4ServiceClient;
import model.ProcessingRequest;
import table.TableCreator;

public class Main {

    private static String apiKey = "<s4-api-key>";
    private static String keySecret = "<s4-key-secret>";
    private static CloseableHttpClient httpClient;

    private static HttpClientContext ctx;

    private static void prepareCredentials() {
        ctx = HttpClientContext.create();
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(apiKey, keySecret);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, creds);
        ctx.setCredentialsProvider(credsProvider);
    }

    private static String output(CloseableHttpResponse response) throws Exception {
        System.out.println(
                response.getStatusLine().getStatusCode() + ":"
                        + response.getStatusLine().getReasonPhrase());

        InputStream content = response.getEntity().getContent();
        StringWriter sw = new StringWriter();
        IOUtils.copy(content, sw, "UTF-8");
        String result = sw.toString();
        return result;
    }

    public static void processInlineDocument(String documentText) throws Exception {
        ProcessingRequest pr = new ProcessingRequest();
        pr.setDocument(documentText);
        pr.setDocumentType("text/plain");

        String serviceEndpointUrl = "https://text.s4.ontotext.com/v1/news";
        HttpPost post = new HttpPost(serviceEndpointUrl);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");

        String message = pr.toJSON();
        post.setEntity(new StringEntity(message, Charset.forName("UTF-8")));

        CloseableHttpResponse response = httpClient.execute(post, ctx);

        System.out.println(output(response));
    }

    public static void processTweet (String tweet) throws Exception {
        ProcessingRequest pr = new ProcessingRequest();
        pr.setDocument(tweet);
        pr.setDocumentType("text/plain");

        String serviceEndpointUrl = "https://text.s4.ontotext.com/v1/twitie";
        HttpPost post = new HttpPost(serviceEndpointUrl);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");

        String message = pr.toJSON();
        post.setEntity(new StringEntity(message, Charset.forName("UTF-8")));

        CloseableHttpResponse response = httpClient.execute(post, ctx);

        System.out.println(output(response));
    }



    public static void processRemoteDocument(String documentUrl) throws Exception {
        ProcessingRequest pr = new ProcessingRequest();
        pr.setDocumentUrl(documentUrl);
        pr.setDocumentType("text/html");

        String serviceEndpointUrl = "https://text.s4.ontotext.com/v1/news";
        HttpPost post = new HttpPost(serviceEndpointUrl);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");

        String message = pr.toJSON();
        post.setEntity(new StringEntity(message, Charset.forName("UTF-8")));

        CloseableHttpResponse response = httpClient.execute(post, ctx);

        System.out.println(output(response));
    }

    public static void processImages(String documentUrl) throws Exception {
        ProcessingRequest pr = new ProcessingRequest();
        pr.setDocumentUrl(documentUrl);
        pr.setDocumentType("text/html");
        pr.setImageTagging(true);
        pr.setImageCategorization(true);

        String serviceEndpointUrl = "https://text.s4.ontotext.com/v1/news";
        HttpPost post = new HttpPost(serviceEndpointUrl);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "application/json");

        String message = pr.toJSON();
        post.setEntity(new StringEntity(message, Charset.forName("UTF-8")));

        CloseableHttpResponse response = httpClient.execute(post, ctx);

        System.out.println(output(response));
    }

    public static void javaSparqlExample(String sparqlQuery) throws Exception {
        String query = URLEncoder.encode(sparqlQuery, "UTF-8");
        String body="query=" + query;

        String serviceEndpointUrl = "https://lod.s4.ontotext.com/v1/FactForge/sparql";
        HttpPost post = new HttpPost(serviceEndpointUrl );
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        post.setHeader("Accept", "application/sparql-results+json");

        post.setEntity(new StringEntity(body, Charset.forName("UTF-8")));

        CloseableHttpResponse response = httpClient.execute(post, ctx);

        String result = output(response);
        System.out.println(TableCreator.createTableFromJSON(result));
    }

    public static void main(String[] args) throws Exception {
        httpClient = HttpClients.createDefault();
        prepareCredentials();

//        javaSparqlExample("SELECT * WHERE { ?s ?p ?o } LIMIT 10");

//        processImages("<document-url-here>");

//        processInlineDocument("<your-text-here>");

//        processRemoteDocument("<document-url-here>");

//        String tweet =
//                "{\"text\":\"Nearly 200,000 people have been killed in #Syria since the start of the conflict in 2011, according to the U.N. http://t.co/pK7t8AD7Xf\","
//                        + "\"lang\":\"en\",\"entities\":{\"symbols\":[],"
//                        + "\"urls\":[{\"expanded_url\":\"http://on.wsj.com/1pZmkY9\",\"indices\":[112,134],\"display_url\":\"on.wsj.com/1pZmkY9\",\"url\":\"http://t.co/pK7t8AD7Xf\"}],"
//                        + "\"hashtags\":[{\"text\":\"Syria\",\"indices\":[42,48]}],"
//                        + "\"user_mentions\":[]},"
//                        + "\"id\":502743846716207104,"
//                        + "\"created_at\":\"Fri Aug 22 09:07:28 +0000 2014\","
//                        + "\"id_str\":\"502743846716207104\"}";
//        processTweet(tweet);
    }
}
