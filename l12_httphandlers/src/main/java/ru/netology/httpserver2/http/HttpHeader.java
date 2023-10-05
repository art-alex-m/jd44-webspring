package ru.netology.httpserver2.http;

public enum HttpHeader {
    CONTENT_TYPE("content-type"),
    CONNECTION("connection"),
    CONTENT_LENGTH("content-length");

    public final String name;

    HttpHeader(String name) {
        this.name = name;
    }

    public static HttpHeader nameof(String name) {
        for (HttpHeader header : HttpHeader.values()) {
            if (header.name.equals(name.toLowerCase())) {
                return header;
            }
        }

        throw new IllegalArgumentException("Unknown header " + name);
    }

    @Override
    public String toString() {
        return name;
    }
}
