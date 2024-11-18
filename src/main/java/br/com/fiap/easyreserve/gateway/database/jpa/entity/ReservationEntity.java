package br.com.fiap.easyreserve.gateway.database.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único no banco de dados

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId; // ID do restaurante associado (FK)

    @Column(name = "customer_name", nullable = false)
    private String customerName; // Nome do cliente

    @Column(name = "customer_contact")
    private String customerContact; // Contato do cliente (ex.: telefone ou e-mail)

    @Column(name = "reservation_date_time", nullable = false)
    private LocalDateTime reservationDateTime; // Data e hora da reserva

    @Column(name = "number_of_guests")
    private int numberOfGuests; // Número de pessoas para a reserva

    private String status; // Status da reserva (ex.: CONFIRMED, CANCELED, COMPLETED)
}
