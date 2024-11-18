package br.com.fiap.easyreserve.controller;

import br.com.fiap.easyreserve.domain.Restaurant;
import br.com.fiap.easyreserve.usecase.CreateRestaurantUseCase;
import br.com.fiap.easyreserve.usecase.FindRestaurantsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final FindRestaurantsUseCase findRestaurantsUseCase;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase,
                                FindRestaurantsUseCase findRestaurantsUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.findRestaurantsUseCase = findRestaurantsUseCase;
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant createdRestaurant = createRestaurantUseCase.execute(restaurant);
        return ResponseEntity.ok(createdRestaurant);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> findRestaurants(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String cuisineType) {
        List<Restaurant> restaurants = findRestaurantsUseCase.execute(location, cuisineType);
        return ResponseEntity.ok(restaurants);
    }
}
