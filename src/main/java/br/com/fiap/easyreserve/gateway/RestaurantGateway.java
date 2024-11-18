package br.com.fiap.easyreserve.gateway;

import br.com.fiap.easyreserve.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {

    Restaurant save(Restaurant restaurant); // Salva ou atualiza um restaurante

    Optional<Restaurant> findById(Long id); // Busca restaurante por ID

    List<Restaurant> findAll(); // Retorna todos os restaurantes

    List<Restaurant> findByCuisineType(String cuisineType); // Busca por tipo de cozinha

    List<Restaurant> findByLocation(String location); // Busca por localização

    void deleteById(Long id); // Remove um restaurante pelo ID
}
