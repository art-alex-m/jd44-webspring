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
    private List<NameValuePair> queryParams;
    private List<NameValuePair> postParams;

    public HttpRequest(HttpMethod method, URI uri, String version, Map<HttpHeader, HeaderEntry> headers) {
        this.uri = uri;
        this.method = method;
        this.headers = headers;
        this.version = version;
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

    HttpRequest setQueryParams(List<NameValuePair> queryParams) {
        this.queryParams = queryParams;
        return this;
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

    public NameValuePair getPostParam(String name) {
        for (NameValuePair pair : postParams) {
            if (pair.getName().equals(name)) {
                return pair;
            }
        }

        return null;
    }

    public List<NameValuePair> getPostParams() {
        return postParams;
    }

    HttpRequest setPostParams(List<NameValuePair> postParams) {
        this.postParams = postParams;
        return this;
    }

    public boolean isXWwwFormUrlencoded() {
        String contentType = getHeader(HttpHeader.CONTENT_TYPE);
        if (contentType == null) return false;
        return contentType.toLowerCase().startsWith("application/x-www-form-urlencoded");
    }

    public int getHeader(HttpHeader key, boolean asInt) {
        HeaderEntry header = headers.getOrDefault(key, null);
        if (header == null) {
            return 0;
        }
        return Integer.parseInt(header.value());
    }

    public String getHeader(HttpHeader key) {
        HeaderEntry header = headers.getOrDefault(key, null);
        if (header == null) {
            return null;
        }
        return header.value();
    }
}
