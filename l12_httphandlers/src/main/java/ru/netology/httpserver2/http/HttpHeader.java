package ru.netology.httpserver2.http;

public enum HttpHeader {
    CONTENT_TYPE("content-type"),
    CONNECTION("connection"),
    CONTENT_LENGTH("content-length");

    public final String name;

    HttpHeader(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
