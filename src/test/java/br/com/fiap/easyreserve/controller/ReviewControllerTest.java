package br.com.fiap.easyreserve.controller;

import br.com.fiap.easyreserve.domain.Review;
import br.com.fiap.easyreserve.usecase.CreateReviewUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private CreateReviewUseCase createReviewUseCase;

    private Review generateReview(Long id, Long restaurantId, String customerName, int rating) {
        return new Review(
                id,
                restaurantId,
                customerName,
                "Excellent food and service!",
                rating
        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateReviewSuccessfully() {
        // Arrange
        Review review = generateReview(null, 1L, "John Doe", 5);
        when(createReviewUseCase.execute(any(Review.class))).thenReturn(review);

        // Act
        ResponseEntity<Review> response = reviewController.createReview(review);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCustomerName()).isEqualTo("John Doe");
        assertThat(response.getBody().getRating()).isEqualTo(5);
        verify(createReviewUseCase, times(1)).execute(review);
    }
}

