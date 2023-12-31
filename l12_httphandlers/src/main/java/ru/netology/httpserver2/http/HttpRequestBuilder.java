package ru.netology.httpserver2.http;

import org.apache.commons.fileupload.FileItem;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestBuilder {
    private final Map<HttpHeader, HeaderEntry> headers = new HashMap<>();

    private HttpMethod method;
    private URI uri;
    private String version;
    private List<NameValuePair> queryParams;
    private List<NameValuePair> postParams;
    private List<FileItem> files;

    public HttpRequest build() {
        return new HttpRequest(method, uri, version, headers)
                .setQueryParams(queryParams)
                .setPostParams(postParams)
                .setFiles(files);
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
        this.queryParams = URLEncodedUtils.parse(uri, StandardCharsets.UTF_8);
        return this;
    }

    public HttpRequestBuilder setUri(String uri) {
        return setUri(URI.create(uri));
    }

    public HttpRequestBuilder setVersion(String version) {
        this.version = version.toUpperCase();
        return this;
    }

    public HttpRequestBuilder setPostParams(String raw) {
        this.postParams = URLEncodedUtils.parse(raw, StandardCharsets.UTF_8);
        return this;
    }

    public HttpRequestBuilder setPostParams(List<FileItem> params) {
        this.postParams = params.stream()
                .filter(FileItem::isFormField)
                .map(p -> new BasicNameValuePair(p.getFieldName(), p.getString()))
                .map(p -> (NameValuePair) p)
                .toList();
        return this;
    }

    public HttpRequestBuilder setFiles(List<FileItem> files) {
        this.files = files.stream()
                .filter(f -> !f.isFormField() && f.getSize() > 0)
                .toList();
        return this;
    }
}
