package br.com.fiap.easyreserve.usecase;

import br.com.fiap.easyreserve.domain.Reservation;
import br.com.fiap.easyreserve.gateway.ReservationGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ManageReservationsUseCaseTest {

    @InjectMocks
    private ManageReservationsUseCase manageReservationsUseCase;

    @Mock
    private ReservationGateway reservationGateway;

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
    void shouldFindReservationsByRestaurant() {
        // Arrange
        Long restaurantId = 1L;
        List<Reservation> reservations = List.of(
                generateReservation(1L, restaurantId, "John Doe"),
                generateReservation(2L, restaurantId, "Jane Smith")
        );
        when(reservationGateway.findByRestaurantId(restaurantId)).thenReturn(reservations);

        // Act
        List<Reservation> result = manageReservationsUseCase.findReservationsByRestaurant(restaurantId);

        // Assert
        assertThat(result).isNotEmpty().hasSize(2);
        assertThat(result).anyMatch(r -> r.getCustomerName().equals("John Doe"));
        assertThat(result).anyMatch(r -> r.getCustomerName().equals("Jane Smith"));
        verify(reservationGateway, times(1)).findByRestaurantId(restaurantId);
    }

    @Test
    void shouldReturnEmptyListWhenNoReservationsForRestaurant() {
        // Arrange
        Long restaurantId = 2L;
        when(reservationGateway.findByRestaurantId(restaurantId)).thenReturn(List.of());

        // Act
        List<Reservation> result = manageReservationsUseCase.findReservationsByRestaurant(restaurantId);

        // Assert
        assertThat(result).isEmpty();
        verify(reservationGateway, times(1)).findByRestaurantId(restaurantId);
    }

    @Test
    void shouldCancelReservationSuccessfully() {
        // Arrange
        Long reservationId = 1L;
        doNothing().when(reservationGateway).deleteById(reservationId);

        // Act
        manageReservationsUseCase.cancelReservation(reservationId);

        // Assert
        verify(reservationGateway, times(1)).deleteById(reservationId);
    }
}
