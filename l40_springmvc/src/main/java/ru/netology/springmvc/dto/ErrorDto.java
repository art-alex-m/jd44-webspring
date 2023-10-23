package ru.netology.springmvc.dto;

import org.springframework.http.HttpStatus;

public class ErrorDto {
    private final String message;
    private final int status;

    public ErrorDto(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
