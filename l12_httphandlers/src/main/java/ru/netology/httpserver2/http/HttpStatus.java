package ru.netology.httpserver2.http;

public enum HttpStatus {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(401, "Bad Request");

    public final int code;
    public final String status;

    HttpStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("HTTP/1.1 %s %s", code, status);
    }
}
