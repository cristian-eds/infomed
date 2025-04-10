package io.github.cristian_eds.InfoMed.exception.custom;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
