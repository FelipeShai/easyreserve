package br.com.fiap.easyreserve.exception;

public class ReviewValidationException extends RuntimeException {
    public ReviewValidationException(String message) {
        super(message);
    }
}
