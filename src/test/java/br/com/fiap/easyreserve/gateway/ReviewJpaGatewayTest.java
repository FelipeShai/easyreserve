package br.com.fiap.easyreserve.gateway;

import br.com.fiap.easyreserve.domain.Review;
import br.com.fiap.easyreserve.gateway.database.jpa.ReviewJpaGateway;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ExtendWith(MockitoExtension.class)
public class ReviewJpaGatewayTest {

    @Autowired
    private ReviewJpaGateway reviewJpaGateway;

    private Review generateReview(Long id, Long restaurantId, String customerName, int rating) {
        return new Review(
                id,
                restaurantId,
                customerName,
                "Excellent service and food!",
                rating
        );
    }

    @Test
    void shouldSaveReviewSuccessfully() {
        // Arrange
        Review review = generateReview(null, 1L, "John Doe", 5);

        // Act
        Review savedReview = reviewJpaGateway.save(review);

        // Assert
        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getCustomerName()).isEqualTo("John Doe");
        assertThat(savedReview.getRating()).isEqualTo(5);
    }

    @Test
    void shouldFindReviewById() {
        // Arrange
        Review review = generateReview(null, 1L, "John Doe", 5);
        Review savedReview = reviewJpaGateway.save(review);

        // Act
        var reviewOptional = reviewJpaGateway.findById(savedReview.getId());

        // Assert
        assertThat(reviewOptional).isPresent();
        reviewOptional.ifPresent(r -> {
            assertThat(r.getId()).isEqualTo(savedReview.getId());
            assertThat(r.getCustomerName()).isEqualTo("John Doe");
            assertThat(r.getRating()).isEqualTo(5);
        });
    }

    @Test
    void shouldReturnEmptyWhenIdDoesNotExist() {
        // Act
        var reviewOptional = reviewJpaGateway.findById(999L);

        // Assert
        assertThat(reviewOptional).isEmpty();
    }

    @Test
    void shouldDeleteReviewById() {
        // Arrange
        Review review = generateReview(null, 1L, "John Doe", 5);
        Review savedReview = reviewJpaGateway.save(review);

        // Act
        reviewJpaGateway.deleteById(savedReview.getId());

        // Assert
        var deletedReviewOptional = reviewJpaGateway.findById(savedReview.getId());
        assertThat(deletedReviewOptional).isEmpty();
    }

    @Test
    void shouldFindReviewsByRestaurantId() {
        // Arrange
        reviewJpaGateway.save(generateReview(null, 1L, "John Doe", 5));
        reviewJpaGateway.save(generateReview(null, 1L, "Jane Smith", 4));

        // Act
        List<Review> reviews = reviewJpaGateway.findByRestaurantId(1L);

        // Assert
        assertThat(reviews).hasSize(2);
        assertThat(reviews).anyMatch(r -> r.getCustomerName().equals("John Doe"));
        assertThat(reviews).anyMatch(r -> r.getCustomerName().equals("Jane Smith"));
    }

    @Test
    void shouldNotThrowExceptionWhenDeletingNonexistentId() {
        // Act
        assertDoesNotThrow(() -> reviewJpaGateway.deleteById(999L));
    }
}
