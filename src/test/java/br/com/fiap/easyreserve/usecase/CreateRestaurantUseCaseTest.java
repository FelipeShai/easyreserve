package br.com.fiap.easyreserve.usecase;

import br.com.fiap.easyreserve.domain.Restaurant;
import br.com.fiap.easyreserve.gateway.RestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateRestaurantUseCaseTest {

    @InjectMocks
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Mock
    private RestaurantGateway restaurantGateway;

    private Restaurant generateRestaurant(String name) {
        return new Restaurant(
                null, // ID será gerado ao salvar
                name,
                "Downtown",
                "Italian",
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
        Restaurant restaurant = generateRestaurant("La Bella");
        when(restaurantGateway.save(any(Restaurant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Restaurant result = createRestaurantUseCase.execute(restaurant);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("La Bella");
        verify(restaurantGateway, times(1)).save(restaurant);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNameIsNull() {
        // Arrange
        Restaurant restaurant = generateRestaurant(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createRestaurantUseCase.execute(restaurant);
        });

        assertThat(exception.getMessage()).isEqualTo("Restaurant name cannot be null or empty");
        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNameIsEmpty() {
        // Arrange
        Restaurant restaurant = generateRestaurant("");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createRestaurantUseCase.execute(restaurant);
        });

        assertThat(exception.getMessage()).isEqualTo("Restaurant name cannot be null or empty");
        verify(restaurantGateway, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNameIsBlank() {
        // Arrange
        Restaurant restaurant = generateRestaurant("   ");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createRestaurantUseCase.execute(restaurant);
        });

        assertThat(exception.getMessage()).isEqualTo("Restaurant name cannot be null or empty");
        verify(restaurantGateway, never()).save(any());
    }
}
