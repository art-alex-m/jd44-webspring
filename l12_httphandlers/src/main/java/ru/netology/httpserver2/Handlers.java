package ru.netology.httpserver2;

import ru.netology.httpserver2.handler.HandlerKey;
import ru.netology.httpserver2.handler.StaticHandlerImpl;
import ru.netology.httpserver2.http.HttpHeader;
import ru.netology.httpserver2.http.HttpMethod;
import ru.netology.httpserver2.http.HttpResponseBuilder;
import ru.netology.httpserver2.http.HttpStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Handlers {
    public static Server apply(Server server) {
        return server
                .addHandler(HandlerKey.defaultKey, new StaticHandlerImpl())
                .addHandler(HttpMethod.GET, "/classic.html", (request, out) -> {
                    Path filePath = Path.of(".", "public", request.getPath());
                    byte[] content = Files.readString(filePath)
                            .replace("{time}", LocalDateTime.now().toString())
                            .getBytes();

                    out.write(HttpResponseBuilder.builder()
                            .setStatus(HttpStatus.OK)
                            .addHeader(HttpHeader.CONTENT_LENGTH, content.length)
                            .addHeader(HttpHeader.CONNECTION, "close")
                            .addHeader(HttpHeader.CONTENT_TYPE, Files.probeContentType(filePath))
                            .getBytes());
                    out.write(content);
                    out.flush();
                });
    }
}
