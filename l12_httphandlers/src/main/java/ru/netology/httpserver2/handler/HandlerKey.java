package ru.netology.httpserver2.handler;

import ru.netology.httpserver2.http.HttpMethod;

public record HandlerKey(HttpMethod method, String path) {
    public static final HandlerKey defaultKey = new HandlerKey(HttpMethod.GET, "*");
}
