package ru.netology.httpserver2.http;

import java.net.URI;
import java.util.Map;

public class HttpRequest {
    private final URI uri;
    private final HttpMethod method;
    private final Map<HttpHeader, HeaderEntry> headers;
    private final String version;

    public HttpRequest(HttpMethod method, URI uri, String version, Map<HttpHeader, HeaderEntry> headers) {
        this.uri = uri;
        this.method = method;
        this.headers = headers;
        this.version = version;
    }

    public String getPath() {
        return uri.getPath();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Map<HttpHeader, HeaderEntry> getHeaders() {
        return headers;
    }

    public String getVersion() {
        return version;
    }
}
