package br.com.fiap.easyreserve.gateway.database.jpa;

import br.com.fiap.easyreserve.domain.Restaurant;
import br.com.fiap.easyreserve.gateway.RestaurantGateway;
import br.com.fiap.easyreserve.gateway.database.jpa.entity.RestaurantEntity;
import br.com.fiap.easyreserve.gateway.database.jpa.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RestaurantJpaGateway implements RestaurantGateway {

    private final RestaurantRepository repository;

    public RestaurantJpaGateway(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantEntity entity = toEntity(restaurant);
        return toDomain(repository.save(entity));
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Restaurant> findByCuisineType(String cuisineType) {
        return repository.findByCuisineType(cuisineType).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Restaurant> findByLocation(String location) {
        return repository.findByLocation(location).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private RestaurantEntity toEntity(Restaurant restaurant) {
        return RestaurantEntity.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .cuisineType(restaurant.getCuisineType())
                .openingHours(restaurant.getOpeningHours())
                .capacity(restaurant.getCapacity())
                .build();
    }

    private Restaurant toDomain(RestaurantEntity entity) {
        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getLocation(),
                entity.getCuisineType(),
                entity.getOpeningHours(),
                entity.getCapacity()
        );
    }
}
