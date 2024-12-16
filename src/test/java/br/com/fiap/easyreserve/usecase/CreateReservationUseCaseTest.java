package br.com.fiap.easyreserve.usecase;

import br.com.fiap.easyreserve.domain.Reservation;
import br.com.fiap.easyreserve.domain.Restaurant;
import br.com.fiap.easyreserve.exception.InvalidReservationException;
import br.com.fiap.easyreserve.exception.ReservationConflictException;
import br.com.fiap.easyreserve.exception.RestaurantNotFoundException;
import br.com.fiap.easyreserve.gateway.ReservationGateway;
import br.com.fiap.easyreserve.gateway.RestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateReservationUseCaseTest {

    @InjectMocks
    private CreateReservationUseCase createReservationUseCase;

    @Mock
    private ReservationGateway reservationGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    private Reservation generateReservation(Long restaurantId, int numberOfGuests, LocalDateTime dateTime) {
        return new Reservation(
                null, // ID será gerado ao salvar
                restaurantId,
                "John Doe",
                "john.doe@example.com",
                dateTime,
                numberOfGuests,
                "CONFIRMED"
        );
    }

    private Restaurant generateRestaurant(Long id, int capacity) {
        return new Restaurant(
                id,
                "La Bella",
                "Downtown",
                "Italian",
                "10:00-22:00",
                capacity
        );
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateReservationSuccessfully() {
        // Arrange
        Reservation reservation = generateReservation(1L, 4, LocalDateTime.now().plusDays(1));
        Restaurant restaurant = generateRestaurant(1L, 50);

        when(restaurantGateway.findById(1L)).thenReturn(Optional.of(restaurant));
        when(reservationGateway.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Reservation result = createReservationUseCase.execute(reservation);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getNumberOfGuests()).isEqualTo(4);
        verify(restaurantGateway, times(1)).findById(1L);
        verify(reservationGateway, times(1)).save(reservation);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantNotFound() {
        // Arrange
        Reservation reservation = generateReservation(999L, 4, LocalDateTime.now().plusDays(1));
        when(restaurantGateway.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            createReservationUseCase.execute(reservation);
        });

        assertThat(exception.getMessage()).isEqualTo("Restaurant with ID 999 not found.");
        verify(restaurantGateway, times(1)).findById(999L);
        verify(reservationGateway, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenReservationDateIsInThePast() {
        // Arrange
        Reservation reservation = generateReservation(1L, 4, LocalDateTime.now().minusDays(1));
        Restaurant restaurant = generateRestaurant(1L, 50);

        when(restaurantGateway.findById(1L)).thenReturn(Optional.of(restaurant));

        // Act & Assert
        InvalidReservationException exception = assertThrows(InvalidReservationException.class, () -> {
            createReservationUseCase.execute(reservation);
        });

        assertThat(exception.getMessage()).isEqualTo("Reservation date must be in the future");
        verify(restaurantGateway, times(1)).findById(1L);
        verify(reservationGateway, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenNumberOfGuestsExceedsCapacity() {
        // Arrange
        Reservation reservation = generateReservation(1L, 60, LocalDateTime.now().plusDays(1));
        Restaurant restaurant = generateRestaurant(1L, 50);

        when(restaurantGateway.findById(1L)).thenReturn(Optional.of(restaurant));

        // Act & Assert
        ReservationConflictException exception = assertThrows(ReservationConflictException.class, () -> {
            createReservationUseCase.execute(reservation);
        });

        assertThat(exception.getMessage()).isEqualTo("Number of guests exceeds restaurant capacity");
        verify(restaurantGateway, times(1)).findById(1L);
        verify(reservationGateway, never()).save(any());
    }
}
