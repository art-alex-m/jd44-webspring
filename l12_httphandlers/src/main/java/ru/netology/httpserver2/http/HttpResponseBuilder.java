package ru.netology.httpserver2.http;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpResponseBuilder {
    private HttpStatus status;
    private final Set<HeaderEntry> headers = new HashSet<>();

    public static HttpResponseBuilder builder() {
        return new HttpResponseBuilder();
    }

    public static HttpResponseBuilder notFound() {
        return HttpResponseBuilder.builder()
                .setStatus(HttpStatus.NOT_FOUND)
                .addHeader(HttpHeader.CONTENT_LENGTH, 0)
                .addHeader(HttpHeader.CONNECTION, "close");
    }

    public static HttpResponseBuilder badRequest() {
        return HttpResponseBuilder.builder()
                .setStatus(HttpStatus.BAD_REQUEST)
                .addHeader(HttpHeader.CONTENT_LENGTH, 0)
                .addHeader(HttpHeader.CONNECTION, "close");
    }

    public static HttpResponseBuilder ok(int contentLength, String contentMimeType) {
        return HttpResponseBuilder.builder()
                .setStatus(HttpStatus.OK)
                .addHeader(HttpHeader.CONTENT_LENGTH, contentLength)
                .addHeader(HttpHeader.CONNECTION, "close")
                .addHeader(HttpHeader.CONTENT_TYPE, contentMimeType);
    }

    public HttpResponseBuilder setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public HttpResponseBuilder addHeader(HttpHeader header, String value) {
        headers.add(new HeaderEntry(header, value));
        return this;
    }

    public HttpResponseBuilder addHeader(HttpHeader header, long value) {
        return addHeader(header, Long.toString(value));
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(status)
                .append(Http.DELIMITER)
                .append(headers.stream().map(Object::toString).collect(Collectors.joining(Http.DELIMITER)))
                .append(Http.DELIMITER)
                .append(Http.DELIMITER)
                .toString();
    }

    public byte[] getBytes() {
        return this.toString().getBytes();
    }
}
