package br.com.fiap.easyreserve.usecase;

import br.com.fiap.easyreserve.domain.Restaurant;
import br.com.fiap.easyreserve.gateway.RestaurantGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindRestaurantsUseCase {

    private final RestaurantGateway restaurantGateway;

    public FindRestaurantsUseCase(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    public List<Restaurant> execute(String location, String cuisineType) {
        if (location != null && !location.isBlank()) {
            return restaurantGateway.findByLocation(location);
        }
        if (cuisineType != null && !cuisineType.isBlank()) {
            return restaurantGateway.findByCuisineType(cuisineType);
        }
        return restaurantGateway.findAll();
    }
}
