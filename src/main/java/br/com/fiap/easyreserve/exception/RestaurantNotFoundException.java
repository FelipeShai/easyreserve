package br.com.fiap.easyreserve.exception;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(Long id) {
        super("Restaurant with ID " + id + " not found.");
    }
}
