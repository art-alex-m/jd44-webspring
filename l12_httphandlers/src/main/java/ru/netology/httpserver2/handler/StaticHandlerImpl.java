package ru.netology.httpserver2.handler;

import ru.netology.httpserver2.http.HttpHeader;
import ru.netology.httpserver2.http.HttpRequest;
import ru.netology.httpserver2.http.HttpResponseBuilder;
import ru.netology.httpserver2.http.HttpStatus;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StaticHandlerImpl implements Handler {
    @Override
    public void handle(HttpRequest request, BufferedOutputStream out) throws IOException {
        Path filePath = Path.of(".", "public", request.getPath());

        if (!Files.isReadable(filePath)) {
            out.write(HttpResponseBuilder.notFound().getBytes());
            out.flush();
            return;
        }

        byte[] response = HttpResponseBuilder.builder()
                .setStatus(HttpStatus.OK)
                .addHeader(HttpHeader.CONTENT_TYPE, Files.probeContentType(filePath))
                .addHeader(HttpHeader.CONNECTION, "close")
                .addHeader(HttpHeader.CONTENT_LENGTH, Files.size(filePath))
                .getBytes();

        out.write(response);
        Files.copy(filePath, out);
        out.flush();
    }
}
