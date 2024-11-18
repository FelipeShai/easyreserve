package br.com.fiap.easyreserve.gateway.database.jpa;

import br.com.fiap.easyreserve.domain.Reservation;
import br.com.fiap.easyreserve.gateway.ReservationGateway;
import br.com.fiap.easyreserve.gateway.database.jpa.entity.ReservationEntity;
import br.com.fiap.easyreserve.gateway.database.jpa.repository.ReservationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReservationJpaGateway implements ReservationGateway {

    private final ReservationRepository repository;

    public ReservationJpaGateway(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = toEntity(reservation);
        return toDomain(repository.save(entity));
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Reservation> findByRestaurantId(Long restaurantId) {
        return repository.findByRestaurantId(restaurantId).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByCustomerName(String customerName) {
        return repository.findByCustomerName(customerName).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private ReservationEntity toEntity(Reservation reservation) {
        return ReservationEntity.builder()
                .id(reservation.getId())
                .restaurantId(reservation.getRestaurantId())
                .customerName(reservation.getCustomerName())
                .customerContact(reservation.getCustomerContact())
                .reservationDateTime(reservation.getReservationDateTime())
                .numberOfGuests(reservation.getNumberOfGuests())
                .status(reservation.getStatus())
                .build();
    }

    private Reservation toDomain(ReservationEntity entity) {
        return new Reservation(
                entity.getId(),
                entity.getRestaurantId(),
                entity.getCustomerName(),
                entity.getCustomerContact(),
                entity.getReservationDateTime(),
                entity.getNumberOfGuests(),
                entity.getStatus()
        );
    }
}
