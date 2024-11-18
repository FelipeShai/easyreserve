package br.com.fiap.easyreserve.gateway.database.jpa.repository;

import br.com.fiap.easyreserve.gateway.database.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    List<RestaurantEntity> findByCuisineType(String cuisineType); // Busca por tipo de cozinha

    List<RestaurantEntity> findByLocation(String location); // Busca por localização
}
