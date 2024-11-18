package br.com.fiap.easyreserve.gateway.database.jpa;

import br.com.fiap.easyreserve.domain.Review;
import br.com.fiap.easyreserve.gateway.ReviewGateway;
import br.com.fiap.easyreserve.gateway.database.jpa.entity.ReviewEntity;
import br.com.fiap.easyreserve.gateway.database.jpa.repository.ReviewRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReviewJpaGateway implements ReviewGateway {

    private final ReviewRepository repository;

    public ReviewJpaGateway(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    public Review save(Review review) {
        ReviewEntity entity = toEntity(review);
        return toDomain(repository.save(entity));
    }

    @Override
    public Optional<Review> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Review> findByRestaurantId(Long restaurantId) {
        return repository.findByRestaurantId(restaurantId).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private ReviewEntity toEntity(Review review) {
        return ReviewEntity.builder()
                .id(review.getId())
                .restaurantId(review.getRestaurantId())
                .customerName(review.getCustomerName())
                .comment(review.getComment())
                .rating(review.getRating())
                .build();
    }

    private Review toDomain(ReviewEntity entity) {
        return new Review(
                entity.getId(),
                entity.getRestaurantId(),
                entity.getCustomerName(),
                entity.getComment(),
                entity.getRating()
        );
    }
}
