package br.com.fiap.easyreserve.usecase;

import br.com.fiap.easyreserve.domain.Review;
import br.com.fiap.easyreserve.exception.RestaurantNotFoundException;
import br.com.fiap.easyreserve.exception.ReviewValidationException;
import br.com.fiap.easyreserve.gateway.RestaurantGateway;
import br.com.fiap.easyreserve.gateway.ReviewGateway;
import org.springframework.stereotype.Service;

@Service
public class CreateReviewUseCase {

    private final ReviewGateway reviewGateway;
    private final RestaurantGateway restaurantGateway;

    public CreateReviewUseCase(ReviewGateway reviewGateway, RestaurantGateway restaurantGateway) {
        this.reviewGateway = reviewGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public Review execute(Review review) {
        // Verificar se o restaurante existe
        restaurantGateway.findById(review.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(review.getRestaurantId()));

        // Validar nota (1 a 5)
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new ReviewValidationException("Rating must be between 1 and 5");
        }

        // Salvar avaliação via gateway
        return reviewGateway.save(review);
    }
}
