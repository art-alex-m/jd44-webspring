package ru.netology.httpserver2.http;

public enum HttpMethod {
    POST("POST"),
    GET("GET");

    public final String value;

    HttpMethod(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
