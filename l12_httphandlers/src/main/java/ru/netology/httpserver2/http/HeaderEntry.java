package ru.netology.httpserver2.http;

public record HeaderEntry(HttpHeader header, String value) {
    public HeaderEntry(HttpHeader header, long value) {
        this(header, Long.toString(value));
    }
    @Override
    public String toString() {
       return String.format("%s: %s", header, value);
    }

    @Override
    public int hashCode() {
        return header.hashCode();
    }
}
