package br.com.fiap.easyreserve.gateway.database.jpa.repository;

import br.com.fiap.easyreserve.gateway.database.jpa.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByRestaurantId(Long restaurantId); // Busca reservas por restaurante

    List<ReservationEntity> findByCustomerName(String customerName); // Busca reservas por nome do cliente
}
