package br.com.fiap.easyreserve.controller;

import br.com.fiap.easyreserve.domain.Reservation;
import br.com.fiap.easyreserve.usecase.CreateReservationUseCase;
import br.com.fiap.easyreserve.usecase.ManageReservationsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private CreateReservationUseCase createReservationUseCase;

    @Mock
    private ManageReservationsUseCase manageReservationsUseCase;

    private Reservation generateReservation(Long id, Long restaurantId, String customerName) {
        return new Reservation(
                id,
                restaurantId,
                customerName,
                "john.doe@example.com",
                LocalDateTime.now().plusDays(1),
                4,
                "CONFIRMED"
        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateReservationSuccessfully() {
        // Arrange
        Reservation reservation = generateReservation(null, 1L, "John Doe");
        when(createReservationUseCase.execute(any(Reservation.class))).thenReturn(reservation);

        // Act
        ResponseEntity<Reservation> response = reservationController.createReservation(reservation);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCustomerName()).isEqualTo("John Doe");
        verify(createReservationUseCase, times(1)).execute(reservation);
    }

    @Test
    void shouldGetReservationsByRestaurantSuccessfully() {
        // Arrange
        Long restaurantId = 1L;
        List<Reservation> reservations = List.of(
                generateReservation(1L, restaurantId, "John Doe"),
                generateReservation(2L, restaurantId, "Jane Smith")
        );
        when(manageReservationsUseCase.findReservationsByRestaurant(restaurantId)).thenReturn(reservations);

        // Act
        ResponseEntity<List<Reservation>> response = reservationController.getReservationsByRestaurant(restaurantId);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull().hasSize(2);
        verify(manageReservationsUseCase, times(1)).findReservationsByRestaurant(restaurantId);
    }

    @Test
    void shouldCancelReservationSuccessfully() {
        // Arrange
        Long reservationId = 1L;
        doNothing().when(manageReservationsUseCase).cancelReservation(reservationId);

        // Act
        ResponseEntity<Void> response = reservationController.cancelReservation(reservationId);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        assertThat(response.getBody()).isNull();
        verify(manageReservationsUseCase, times(1)).cancelReservation(reservationId);
    }
}
