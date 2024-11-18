package br.com.fiap.easyreserve.usecase;

import br.com.fiap.easyreserve.domain.Restaurant;
import br.com.fiap.easyreserve.gateway.RestaurantGateway;
import org.springframework.stereotype.Service;

@Service
public class CreateRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public CreateRestaurantUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public Restaurant execute(Restaurant restaurant) {
        // Validação de dados (se necessário)
        if (restaurant.getName() == null || restaurant.getName().isBlank()) {
            throw new IllegalArgumentException("Restaurant name cannot be null or empty");
        }

        // Salvar restaurante via gateway
        return restaurantGateway.save(restaurant);
    }
}
