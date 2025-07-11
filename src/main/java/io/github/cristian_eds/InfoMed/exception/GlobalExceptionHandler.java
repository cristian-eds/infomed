package io.github.cristian_eds.InfoMed.exception;

import io.github.cristian_eds.InfoMed.controller.dto.ResponseError;
import io.github.cristian_eds.InfoMed.exception.custom.EmailAlreadyExistsException;
import io.github.cristian_eds.InfoMed.exception.custom.FileOperationException;
import io.github.cristian_eds.InfoMed.exception.custom.IncorrectPasswordException;
import io.github.cristian_eds.InfoMed.exception.custom.InvalidLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleDateTimeParse(DateTimeParseException e) {
        return new ResponseError("DateTime invalid, please try again with valid datetime.",HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleIncorrectPassword(IncorrectPasswordException e) {
        return new ResponseError(e.getMessage(),HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleNoSuchElement(NoSuchElementException e) {
        return new ResponseError(e.getMessage(),HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleUsernameNotFound(UsernameNotFoundException e) {
        return new ResponseError(e.getMessage(),HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseError handleInvalidLogin(InvalidLoginException e) {
        return new ResponseError(e.getMessage(),HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleEmailDupicateRegister(EmailAlreadyExistsException e) {
        return new ResponseError(e.getMessage(), HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler(FileOperationException.class)
    public ResponseError handleFileOperationException(FileOperationException e) {
        return new ResponseError(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleInternalError(RuntimeException e) {
        return new ResponseError("Internal error, please try again later.",HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
