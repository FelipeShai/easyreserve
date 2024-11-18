package br.com.fiap.easyreserve.controller;

import br.com.fiap.easyreserve.domain.Reservation;
import br.com.fiap.easyreserve.usecase.CreateReservationUseCase;
import br.com.fiap.easyreserve.usecase.ManageReservationsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final CreateReservationUseCase createReservationUseCase;
    private final ManageReservationsUseCase manageReservationsUseCase;

    public ReservationController(CreateReservationUseCase createReservationUseCase,
                                 ManageReservationsUseCase manageReservationsUseCase) {
        this.createReservationUseCase = createReservationUseCase;
        this.manageReservationsUseCase = manageReservationsUseCase;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation createdReservation = createReservationUseCase.execute(reservation);
        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Reservation>> getReservationsByRestaurant(@PathVariable Long restaurantId) {
        List<Reservation> reservations = manageReservationsUseCase.findReservationsByRestaurant(restaurantId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        manageReservationsUseCase.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
