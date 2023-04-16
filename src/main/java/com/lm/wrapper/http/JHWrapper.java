package com.lm.wrapper.http;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Map;

public class JHWrapper {

    private JHWrapper() {}

    private static HttpResponse<String> makeCall(
            HttpRequest.Builder requestBuilder,
            String baseUrl,
            String path,
            Map<String, String> headers,
            Map<String, String> queryParams,
            Map<String, String> uriParams
    ) throws IOException, InterruptedException {

        var uriBuilder = new UriBuilder(baseUrl).path(path);

        // fill the values of the builders with the values provided
        // and create the instances for the uri and the request
        headers.forEach(requestBuilder::header);
        uriBuilder.queryValues(queryParams);
        uriBuilder.urlValues(uriParams);
        requestBuilder.uri(uriBuilder.build());

        var request = requestBuilder.build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> remoteGetCall(
            String baseUrl,
            String path,
            Map<String, String> headers,
            Map<String, String> queryParams,
            Map<String, String> uriParams
    ) throws IOException, InterruptedException {
        var requestBuilder = HttpRequest.newBuilder().GET().version(HttpClient.Version.HTTP_2);
        return makeCall(requestBuilder,baseUrl,path,headers,queryParams,uriParams);
    }

    public static HttpResponse<String> remoteGetCall(
            String baseUrl,
            String path,
            Map<String, String> headers,
            Map<String, String> uriParams
    ) throws IOException, InterruptedException {
        return remoteGetCall(baseUrl, path, headers,  Map.of(), uriParams);
    }

    public static HttpResponse<String> remoteGetCall(
            String baseUrl,
            String path,
            Map<String, String> uriParams
    ) throws IOException, InterruptedException {
        return remoteGetCall(baseUrl, path, Map.of(),  Map.of(), uriParams);
    }

    public static HttpResponse<String> remoteGetCall(String url) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(
                HttpRequest.newBuilder().GET().version(HttpClient.Version.HTTP_2).uri(URI.create(url)).build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    public static HttpResponse<String> remotePostCall(
            String baseUrl,
            String path,
            Map<String, String> headers,
            Map<String, String> queryParams,
            Map<String, String> uriParams,
            String body
    ) throws IOException, InterruptedException {
        var requestBuilder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .version(HttpClient.Version.HTTP_2);
        return makeCall(requestBuilder,baseUrl,path,headers,queryParams,uriParams);
    }

    public static HttpResponse<String> remotePostCall(
            String baseUrl,
            String path,
            Map<String, String> headers,
            Map<String, String> queryParams,
            Map<String, String> uriParams
    ) throws IOException, InterruptedException {
        var requestBuilder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .version(HttpClient.Version.HTTP_2);
        return makeCall(requestBuilder,baseUrl,path,headers,queryParams,uriParams);
    }

    public static HttpResponse<String> remotePostCall(
            String baseUrl,
            String path,
            Map<String, String> headers,
            Map<String, String> uriParams,
            String body
    ) throws IOException, InterruptedException {
        return remotePostCall(baseUrl, path, headers, Map.of(), uriParams, body);
    }

    public static HttpResponse<String> remotePostCall(
            String baseUrl,
            String path,
            Map<String, String> headers,
            Map<String, String> uriParams
    ) throws IOException, InterruptedException {
        return remotePostCall(baseUrl, path, headers, Map.of(), uriParams);
    }

    public static HttpResponse<String> remotePostCall(
            String baseUrl,
            String path,
            Map<String, String> uriParams,
            String body
    ) throws IOException, InterruptedException {
        return remotePostCall(baseUrl, path, Map.of(), Map.of(), uriParams, body);
    }

    public static HttpResponse<String> remotePostCall(
            String baseUrl,
            String path,
            Map<String, String> uriParams
    ) throws IOException, InterruptedException {
        return remotePostCall(baseUrl, path, Map.of(), Map.of(), uriParams);
    }

    public static HttpResponse<String> remotePostCall(
            String url,
            String body
    ) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(
                HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(body))
                        .version(HttpClient.Version.HTTP_2).uri(URI.create(url)).build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    public static HttpResponse<String> remotePostCall(
            String url
    ) throws IOException, InterruptedException {
        return HttpClient.newHttpClient().send(
                HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.noBody())
                        .version(HttpClient.Version.HTTP_2).uri(URI.create(url)).build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

}
