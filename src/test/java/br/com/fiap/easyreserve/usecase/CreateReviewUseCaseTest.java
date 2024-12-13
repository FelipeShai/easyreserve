package br.com.fiap.easyreserve.usecase;

import br.com.fiap.easyreserve.domain.Restaurant;
import br.com.fiap.easyreserve.domain.Review;
import br.com.fiap.easyreserve.exception.RestaurantNotFoundException;
import br.com.fiap.easyreserve.exception.ReviewValidationException;
import br.com.fiap.easyreserve.gateway.RestaurantGateway;
import br.com.fiap.easyreserve.gateway.ReviewGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateReviewUseCaseTest {

    @InjectMocks
    private CreateReviewUseCase createReviewUseCase;

    @Mock
    private ReviewGateway reviewGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    private Review generateReview(Long restaurantId, String customerName, int rating) {
        return new Review(
                null, // ID será gerado ao salvar
                restaurantId,
                customerName,
                "Great food and service!",
                rating
        );
    }

    private Restaurant generateRestaurant(Long id, String name) {
        return new Restaurant(id, name, "Downtown", "Italian", "10:00-22:00", 50);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateReviewSuccessfully() {
        // Arrange
        Review review = generateReview(1L, "John Doe", 5);
        when(restaurantGateway.findById(1L)).thenReturn(Optional.of(generateRestaurant(1L, "La Bella")));
        when(reviewGateway.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Review result = createReviewUseCase.execute(review);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCustomerName()).isEqualTo("John Doe");
        assertThat(result.getRating()).isEqualTo(5);
        verify(restaurantGateway, times(1)).findById(1L);
        verify(reviewGateway, times(1)).save(review);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {
        // Arrange
        Review review = generateReview(999L, "Jane Doe", 4);
        when(restaurantGateway.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            createReviewUseCase.execute(review);
        });

        assertThat(exception.getMessage()).isEqualTo("Restaurant with ID 999 not found.");
        verify(restaurantGateway, times(1)).findById(999L);
        verify(reviewGateway, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenRatingIsInvalid() {
        // Arrange
        Review review = generateReview(1L, "John Doe", 6); // Rating inválido
        when(restaurantGateway.findById(1L)).thenReturn(Optional.of(generateRestaurant(1L, "La Bella")));

        // Act & Assert
        ReviewValidationException exception = assertThrows(ReviewValidationException.class, () -> {
            createReviewUseCase.execute(review);
        });

        assertThat(exception.getMessage()).isEqualTo("Rating must be between 1 and 5");
        verify(restaurantGateway, times(1)).findById(1L);
        verify(reviewGateway, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenRatingIsBelowMinimum() {
        // Arrange
        Review review = generateReview(1L, "John Doe", 0); // Rating abaixo do mínimo
        when(restaurantGateway.findById(1L)).thenReturn(Optional.of(generateRestaurant(1L, "La Bella")));

        // Act & Assert
        ReviewValidationException exception = assertThrows(ReviewValidationException.class, () -> {
            createReviewUseCase.execute(review);
        });

        assertThat(exception.getMessage()).isEqualTo("Rating must be between 1 and 5");
        verify(restaurantGateway, times(1)).findById(1L);
        verify(reviewGateway, never()).save(any());
    }
}
