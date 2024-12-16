package br.com.fiap.easyreserve.controller;

import br.com.fiap.easyreserve.domain.Restaurant;
import br.com.fiap.easyreserve.usecase.CreateRestaurantUseCase;
import br.com.fiap.easyreserve.usecase.FindRestaurantsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestaurantControllerTest {

    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Mock
    private FindRestaurantsUseCase findRestaurantsUseCase;

    private Restaurant generateRestaurant(Long id, String name, String location, String cuisineType) {
        return new Restaurant(
                id,
                name,
                location,
                cuisineType,
                "10:00-22:00",
                50
        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateRestaurantSuccessfully() {
        // Arrange
        Restaurant restaurant = generateRestaurant(null, "La Bella", "Downtown", "Italian");
        when(createRestaurantUseCase.execute(any(Restaurant.class))).thenReturn(restaurant);

        // Act
        ResponseEntity<Restaurant> response = restaurantController.createRestaurant(restaurant);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("La Bella");
        verify(createRestaurantUseCase, times(1)).execute(restaurant);
    }

    @Test
    void shouldFindRestaurantsByLocation() {
        // Arrange
        String location = "Downtown";
        List<Restaurant> restaurants = List.of(
                generateRestaurant(1L, "La Bella", location, "Italian"),
                generateRestaurant(2L, "Berimbau", location, "Brazilian")
        );
        when(findRestaurantsUseCase.execute(location, null)).thenReturn(restaurants);

        // Act
        ResponseEntity<List<Restaurant>> response = restaurantController.findRestaurants(location, null);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull().hasSize(2);
        verify(findRestaurantsUseCase, times(1)).execute(location, null);
    }

    @Test
    void shouldFindRestaurantsByCuisineType() {
        // Arrange
        String cuisineType = "Italian";
        List<Restaurant> restaurants = List.of(
                generateRestaurant(1L, "La Bella", "Downtown", cuisineType),
                generateRestaurant(2L, "Pasta Place", "Midtown", cuisineType)
        );
        when(findRestaurantsUseCase.execute(null, cuisineType)).thenReturn(restaurants);

        // Act
        ResponseEntity<List<Restaurant>> response = restaurantController.findRestaurants(null, cuisineType);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull().hasSize(2);
        verify(findRestaurantsUseCase, times(1)).execute(null, cuisineType);
    }

    @Test
    void shouldFindAllRestaurantsWhenNoFiltersProvided() {
        // Arrange
        List<Restaurant> restaurants = List.of(
                generateRestaurant(1L, "La Bella", "Downtown", "Italian"),
                generateRestaurant(2L, "Berimbau", "Midtown", "Brazilian")
        );
        when(findRestaurantsUseCase.execute(null, null)).thenReturn(restaurants);

        // Act
        ResponseEntity<List<Restaurant>> response = restaurantController.findRestaurants(null, null);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull().hasSize(2);
        verify(findRestaurantsUseCase, times(1)).execute(null, null);
    }
}
