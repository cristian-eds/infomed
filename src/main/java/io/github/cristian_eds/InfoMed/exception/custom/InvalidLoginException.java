package io.github.cristian_eds.InfoMed.exception.custom;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
