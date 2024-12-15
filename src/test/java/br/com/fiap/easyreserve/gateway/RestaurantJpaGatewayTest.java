package br.com.fiap.easyreserve.gateway;

import br.com.fiap.easyreserve.domain.Restaurant;
import br.com.fiap.easyreserve.gateway.database.jpa.RestaurantJpaGateway;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ExtendWith(MockitoExtension.class)
public class RestaurantJpaGatewayTest {

    @Autowired
    private RestaurantJpaGateway restaurantJpaGateway;

    private Restaurant generateRestaurant(String name) {
        return new Restaurant(null, name, "Downtown", "Italian", "10:00-22:00", 50);
    }

    @Test
    void shouldDeleteRestaurantById() {
        // Arrange
        Restaurant restaurant = generateRestaurant("Berimbau");
        Restaurant savedRestaurant = restaurantJpaGateway.save(restaurant);

        // Act
        restaurantJpaGateway.deleteById(savedRestaurant.getId());

        // Assert
        var deletedRestaurantOptional = restaurantJpaGateway.findById(savedRestaurant.getId());
        assertThat(deletedRestaurantOptional).isEmpty();
    }

    @Test
    void shouldListAllRestaurants() {
        // Arrange
        restaurantJpaGateway.save(generateRestaurant("Berimbau"));

        // Act
        var restaurants = restaurantJpaGateway.findAll();
        restaurants.forEach(r -> System.out.println("ID: " + r.getId() + ", Nome: " + r.getName()));

        // Assert
        assertThat(restaurants).isNotEmpty();
        assertThat(restaurants).anyMatch(r -> r.getName().equals("Berimbau"));
    }

    @Test
    void shouldFindRestaurantById() {
        // Arrange
        Restaurant savedRestaurant = restaurantJpaGateway.save(generateRestaurant("Berimbau"));

        // Act
        var restaurantOptional = restaurantJpaGateway.findById(savedRestaurant.getId());

        // Assert
        assertThat(restaurantOptional).isPresent();
        restaurantOptional.ifPresent(r -> {
            assertThat(r.getId()).isEqualTo(savedRestaurant.getId());
            assertThat(r.getName()).isEqualTo("Berimbau");
        });
    }

    @Test
    void shouldSaveRestaurant() {
        // Arrange
        Restaurant restaurant = generateRestaurant("La Bella");

        // Act
        Restaurant savedRestaurant = restaurantJpaGateway.save(restaurant);

        // Assert
        assertThat(savedRestaurant).isNotNull();
        assertThat(savedRestaurant.getName()).isEqualTo("La Bella");
    }

    @Test
    void shouldReturnEmptyWhenIdDoesNotExist() {
        // Act
        var restaurantOptional = restaurantJpaGateway.findById(999L);

        // Assert
        assertThat(restaurantOptional).isEmpty();
    }

    @Test
    void shouldUpdateRestaurant() {
        // Arrange
        Restaurant restaurant = generateRestaurant("Koba Korean Bbq");
        Restaurant savedRestaurant = restaurantJpaGateway.save(restaurant);

        // Atualizar informações
        savedRestaurant.setName("Koba Korean Bbq Updated");
        savedRestaurant.setLocation("Uptown");

        // Act
        Restaurant updatedRestaurant = restaurantJpaGateway.save(savedRestaurant);

        // Assert
        assertThat(updatedRestaurant.getName()).isEqualTo("Koba Korean Bbq Updated");
        assertThat(updatedRestaurant.getLocation()).isEqualTo("Uptown");
    }
}