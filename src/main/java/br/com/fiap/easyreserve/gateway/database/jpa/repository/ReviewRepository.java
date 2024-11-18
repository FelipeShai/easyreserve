package br.com.fiap.easyreserve.gateway.database.jpa.repository;

import br.com.fiap.easyreserve.gateway.database.jpa.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByRestaurantId(Long restaurantId); // Busca avaliações por restaurante
}
