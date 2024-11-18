package br.com.fiap.easyreserve.usecase;

import br.com.fiap.easyreserve.domain.Reservation;
import br.com.fiap.easyreserve.gateway.ReservationGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageReservationsUseCase {

    private final ReservationGateway reservationGateway;

    public ManageReservationsUseCase(ReservationGateway reservationGateway) {
        this.reservationGateway = reservationGateway;
    }

    public List<Reservation> findReservationsByRestaurant(Long restaurantId) {
        return reservationGateway.findByRestaurantId(restaurantId);
    }

    public void cancelReservation(Long reservationId) {
        reservationGateway.deleteById(reservationId);
    }
}
