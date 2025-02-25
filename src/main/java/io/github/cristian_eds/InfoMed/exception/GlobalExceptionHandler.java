package io.github.cristian_eds.InfoMed.exception;

import io.github.cristian_eds.InfoMed.controller.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleErrosNaoTratados(RuntimeException e) {
        return new ResponseError("Internal errro, please try again later.",HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
