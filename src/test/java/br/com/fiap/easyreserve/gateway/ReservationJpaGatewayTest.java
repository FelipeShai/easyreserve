package br.com.fiap.easyreserve.gateway;

import br.com.fiap.easyreserve.domain.Reservation;
import br.com.fiap.easyreserve.gateway.database.jpa.ReservationJpaGateway;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ExtendWith(MockitoExtension.class)
public class ReservationJpaGatewayTest {

    @Autowired
    private ReservationJpaGateway reservationJpaGateway;

    private Reservation generateReservation(Long id, Long restaurantId, String customerName) {
        return new Reservation(
                id,
                restaurantId,
                customerName,
                "123456789",
                LocalDateTime.now().plusDays(1),
                4,
                "CONFIRMED"
        );
    }

    @Test
    void shouldSaveReservationSuccessfully() {
        // Arrange
        Reservation reservation = generateReservation(null, 1L, "John Doe");

        // Act
        Reservation savedReservation = reservationJpaGateway.save(reservation);

        // Assert
        assertThat(savedReservation).isNotNull();
        assertThat(savedReservation.getCustomerName()).isEqualTo("John Doe");
    }

    @Test
    void shouldFindReservationById() {
        // Arrange
        Reservation reservation = generateReservation(null, 1L, "John Doe");
        Reservation savedReservation = reservationJpaGateway.save(reservation);

        // Act
        var reservationOptional = reservationJpaGateway.findById(savedReservation.getId());

        // Assert
        assertThat(reservationOptional).isPresent();
        reservationOptional.ifPresent(r -> {
            assertThat(r.getId()).isEqualTo(savedReservation.getId());
            assertThat(r.getCustomerName()).isEqualTo("John Doe");
        });
    }

    @Test
    void shouldReturnEmptyWhenIdDoesNotExist() {
        // Act
        var reservationOptional = reservationJpaGateway.findById(999L);

        // Assert
        assertThat(reservationOptional).isEmpty();
    }

    @Test
    void shouldDeleteReservationById() {
        // Arrange
        Reservation reservation = generateReservation(null, 1L, "John Doe");
        Reservation savedReservation = reservationJpaGateway.save(reservation);

        // Act
        reservationJpaGateway.deleteById(savedReservation.getId());

        // Assert
        var deletedReservationOptional = reservationJpaGateway.findById(savedReservation.getId());
        assertThat(deletedReservationOptional).isEmpty();
    }

    @Test
    void shouldFindReservationsByRestaurantId() {
        // Arrange
        reservationJpaGateway.save(generateReservation(null, 1L, "John Doe"));
        reservationJpaGateway.save(generateReservation(null, 1L, "Jane Smith"));

        // Act
        List<Reservation> reservations = reservationJpaGateway.findByRestaurantId(1L);

        // Assert
        assertThat(reservations).hasSize(2);
        assertThat(reservations).anyMatch(r -> r.getCustomerName().equals("John Doe"));
        assertThat(reservations).anyMatch(r -> r.getCustomerName().equals("Jane Smith"));
    }

    @Test
    void shouldFindReservationsByCustomerName() {
        // Arrange
        reservationJpaGateway.save(generateReservation(null, 1L, "John Doe"));
        reservationJpaGateway.save(generateReservation(null, 2L, "John Doe"));

        // Act
        List<Reservation> reservations = reservationJpaGateway.findByCustomerName("John Doe");

        // Assert
        assertThat(reservations).hasSize(2);
        reservations.forEach(r -> assertThat(r.getCustomerName()).isEqualTo("John Doe"));
    }

    @Test
    void shouldNotThrowExceptionWhenDeletingNonexistentId() {
        // Act
        assertDoesNotThrow(() -> reservationJpaGateway.deleteById(999L));
    }
}