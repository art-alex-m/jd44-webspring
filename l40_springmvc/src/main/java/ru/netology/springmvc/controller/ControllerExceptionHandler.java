package ru.netology.springmvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.springmvc.dto.ErrorDto;
import ru.netology.springmvc.exception.NotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleNotFoundException(RuntimeException ex) {
        return new ErrorDto(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
