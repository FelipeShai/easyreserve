package br.com.fiap.easyreserve.gateway;

import br.com.fiap.easyreserve.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewGateway {

    Review save(Review review); // Salva ou atualiza uma avaliação

    Optional<Review> findById(Long id); // Busca avaliação por ID

    List<Review> findByRestaurantId(Long restaurantId); // Busca avaliações de um restaurante

    void deleteById(Long id); // Remove uma avaliação pelo ID
}
