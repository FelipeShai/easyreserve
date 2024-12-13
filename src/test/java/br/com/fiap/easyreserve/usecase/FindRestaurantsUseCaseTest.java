package br.com.fiap.easyreserve.usecase;

import br.com.fiap.easyreserve.domain.Restaurant;
import br.com.fiap.easyreserve.gateway.RestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FindRestaurantsUseCaseTest {

    @InjectMocks
    private FindRestaurantsUseCase findRestaurantsUseCase;

    @Mock
    private RestaurantGateway restaurantGateway;

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
    void shouldFindRestaurantsByLocation() {
        // Arrange
        String location = "Downtown";
        List<Restaurant> restaurants = List.of(
                generateRestaurant(1L, "La Bella", location, "Italian"),
                generateRestaurant(2L, "Berimbau", location, "Brazilian")
        );
        when(restaurantGateway.findByLocation(location)).thenReturn(restaurants);

        // Act
        List<Restaurant> result = findRestaurantsUseCase.execute(location, null);

        // Assert
        assertThat(result).isNotEmpty().hasSize(2);
        verify(restaurantGateway, times(1)).findByLocation(location);
        verify(restaurantGateway, never()).findByCuisineType(any());
        verify(restaurantGateway, never()).findAll();
    }

    @Test
    void shouldFindRestaurantsByCuisineType() {
        // Arrange
        String cuisineType = "Italian";
        List<Restaurant> restaurants = List.of(
                generateRestaurant(1L, "La Bella", "Downtown", cuisineType),
                generateRestaurant(2L, "Pasta Place", "Midtown", cuisineType)
        );
        when(restaurantGateway.findByCuisineType(cuisineType)).thenReturn(restaurants);

        // Act
        List<Restaurant> result = findRestaurantsUseCase.execute(null, cuisineType);

        // Assert
        assertThat(result).isNotEmpty().hasSize(2);
        verify(restaurantGateway, times(1)).findByCuisineType(cuisineType);
        verify(restaurantGateway, never()).findByLocation(any());
        verify(restaurantGateway, never()).findAll();
    }

    @Test
    void shouldFindAllRestaurantsWhenNoFiltersProvided() {
        // Arrange
        List<Restaurant> restaurants = List.of(
                generateRestaurant(1L, "La Bella", "Downtown", "Italian"),
                generateRestaurant(2L, "Berimbau", "Midtown", "Brazilian")
        );
        when(restaurantGateway.findAll()).thenReturn(restaurants);

        // Act
        List<Restaurant> result = findRestaurantsUseCase.execute(null, null);

        // Assert
        assertThat(result).isNotEmpty().hasSize(2);
        verify(restaurantGateway, times(1)).findAll();
        verify(restaurantGateway, never()).findByLocation(any());
        verify(restaurantGateway, never()).findByCuisineType(any());
    }

    @Test
    void shouldReturnEmptyListWhenNoRestaurantsMatchLocation() {
        // Arrange
        String location = "Nonexistent";
        when(restaurantGateway.findByLocation(location)).thenReturn(List.of());

        // Act
        List<Restaurant> result = findRestaurantsUseCase.execute(location, null);

        // Assert
        assertThat(result).isEmpty();
        verify(restaurantGateway, times(1)).findByLocation(location);
    }

    @Test
    void shouldReturnEmptyListWhenNoRestaurantsMatchCuisineType() {
        // Arrange
        String cuisineType = "Nonexistent";
        when(restaurantGateway.findByCuisineType(cuisineType)).thenReturn(List.of());

        // Act
        List<Restaurant> result = findRestaurantsUseCase.execute(null, cuisineType);

        // Assert
        assertThat(result).isEmpty();
        verify(restaurantGateway, times(1)).findByCuisineType(cuisineType);
    }
}
