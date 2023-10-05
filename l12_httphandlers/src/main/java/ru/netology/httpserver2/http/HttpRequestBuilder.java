package ru.netology.httpserver2.http;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBuilder {
    private final Map<HttpHeader, HeaderEntry> headers = new HashMap<>();

    private HttpMethod method;
    private URI uri;
    private String version;

    public HttpRequest build() {
        return new HttpRequest(method, uri, version, headers);
    }

    public HttpRequestBuilder addHeader(HttpHeader header, String value) {
        headers.put(header, new HeaderEntry(header, value));
        return this;
    }

    public HttpRequestBuilder addHeader(String header, String value) throws IllegalArgumentException {
        return addHeader(HttpHeader.nameof(header), value);
    }

    public HttpRequestBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public HttpRequestBuilder setMethod(String method) throws IllegalArgumentException {
        return setMethod(HttpMethod.valueOf(method.toUpperCase()));
    }

    public HttpRequestBuilder setUri(URI uri) {
        this.uri = uri;
        return this;
    }

    public HttpRequestBuilder setUri(String uri) {
        return setUri(URI.create(uri));
    }

    public HttpRequestBuilder setVersion(String version) {
        this.version = version.toUpperCase();
        return this;
    }
}
