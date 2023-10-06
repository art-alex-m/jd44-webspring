package ru.netology.httpserver2.server;

import ru.netology.httpserver2.handler.Handler;
import ru.netology.httpserver2.handler.HandlerKey;
import ru.netology.httpserver2.http.HttpRequest;
import ru.netology.httpserver2.http.HttpRequestParser;
import ru.netology.httpserver2.http.HttpResponseBuilder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class HandlerTask implements Runnable {

    private final static List<String> VALID_PATHS = List.of("/index.html", "/spring.svg", "/spring.png",
            "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html",
            "/events.html", "/events.js");

    private final Socket socket;
    private final Map<HandlerKey, Handler> handlers;

    public HandlerTask(Socket socket, Map<HandlerKey, Handler> handlers) {
        this.socket = socket;
        this.handlers = handlers;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream())) {

            HttpRequest request = HttpRequestParser.parse(in);

            System.out.println("Accept new request " + request);

            if (!VALID_PATHS.contains(request.getPath())) {
                out.write(HttpResponseBuilder.notFound().getBytes());
                out.flush();
                return;
            }

            Handler defaultHandler = handlers.get(HandlerKey.defaultKey);
            handlers.getOrDefault(new HandlerKey(request.getMethod(), request.getPath()), defaultHandler)
                    .handle(request, out);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
