package ru.netology.httpserver2.http;

import org.apache.commons.fileupload.RequestContext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HttpRequestContext implements RequestContext {
    private final String contentType;
    private final InputStream inputStream;

    public HttpRequestContext(String contentType, InputStream inputStream) {
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    @Override
    public String getCharacterEncoding() {
        return StandardCharsets.UTF_8.toString();
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }
}
