package br.com.fiap.easyreserve.controller;

import br.com.fiap.easyreserve.domain.Review;
import br.com.fiap.easyreserve.usecase.CreateReviewUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final CreateReviewUseCase createReviewUseCase;

    public ReviewController(CreateReviewUseCase createReviewUseCase) {
        this.createReviewUseCase = createReviewUseCase;
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review createdReview = createReviewUseCase.execute(review);
        return ResponseEntity.ok(createdReview);
    }
}
