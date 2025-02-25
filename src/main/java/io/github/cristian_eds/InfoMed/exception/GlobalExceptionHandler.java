package io.github.cristian_eds.InfoMed.exception;

import io.github.cristian_eds.InfoMed.controller.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleErrosNaoTratados(DateTimeParseException e) {
        return new ResponseError("DateTime invalid, please try again with valid datetime.",HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleErrosNaoTratados(NoSuchElementException e) {
        return new ResponseError(e.getMessage(),HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleErrosNaoTratados(RuntimeException e) {
        return new ResponseError("Internal errro, please try again later.",HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
