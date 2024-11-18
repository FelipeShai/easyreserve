package br.com.fiap.easyreserve.usecase;

import br.com.fiap.easyreserve.domain.Reservation;
import br.com.fiap.easyreserve.exception.InvalidReservationException;
import br.com.fiap.easyreserve.exception.ReservationConflictException;
import br.com.fiap.easyreserve.exception.RestaurantNotFoundException;
import br.com.fiap.easyreserve.gateway.ReservationGateway;
import br.com.fiap.easyreserve.gateway.RestaurantGateway;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateReservationUseCase {

    private final ReservationGateway reservationGateway;
    private final RestaurantGateway restaurantGateway;

    public CreateReservationUseCase(ReservationGateway reservationGateway, RestaurantGateway restaurantGateway) {
        this.reservationGateway = reservationGateway;
        this.restaurantGateway = restaurantGateway;
    }

    public Reservation execute(Reservation reservation) {
        // Verificar se o restaurante existe
        var restaurant = restaurantGateway.findById(reservation.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException(reservation.getRestaurantId()));

        // Verificar se a reserva é válida
        if (reservation.getReservationDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidReservationException("Reservation date must be in the future");
        }

        if (reservation.getNumberOfGuests() > restaurant.getCapacity()) {
            throw new ReservationConflictException("Number of guests exceeds restaurant capacity");
        }

        // Salvar reserva via gateway
        return reservationGateway.save(reservation);
    }
}
