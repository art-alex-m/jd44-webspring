package ru.netology.httpserver2.handler;

import ru.netology.httpserver2.http.HttpRequest;

import java.io.BufferedOutputStream;
import java.io.IOException;

@FunctionalInterface
public interface Handler {
    void handle(HttpRequest request, BufferedOutputStream out) throws IOException;
}
