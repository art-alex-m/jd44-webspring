package ru.netology.httpserver2.http;

import org.apache.http.NameValuePair;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private final URI uri;
    private final HttpMethod method;
    private final Map<HttpHeader, HeaderEntry> headers;
    private final String version;
    private final List<NameValuePair> queryParams;

    public HttpRequest(HttpMethod method, URI uri, String version, Map<HttpHeader, HeaderEntry> headers,
            List<NameValuePair> queryParams) {
        this.uri = uri;
        this.method = method;
        this.headers = headers;
        this.version = version;
        this.queryParams = queryParams;
    }

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
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

    @Override
    public String toString() {
        return String.format("[%s %s %s]", method, uri, version);
    }

    public NameValuePair getQueryParam(String name) {
        for (NameValuePair pair : queryParams) {
            if (pair.getName().equals(name)) {
                return pair;
            }
        }

        return null;
    }

    public List<NameValuePair> getQueryParams() {
        return queryParams;
    }
}
