package br.com.fiap.easyreserve.gateway;

import br.com.fiap.easyreserve.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationGateway {

    Reservation save(Reservation reservation); // Salva ou atualiza uma reserva

    Optional<Reservation> findById(Long id); // Busca reserva por ID

    List<Reservation> findByRestaurantId(Long restaurantId); // Busca reservas por restaurante

    List<Reservation> findByCustomerName(String customerName); // Busca reservas por nome do cliente

    void deleteById(Long id); // Cancela uma reserva pelo ID
}
