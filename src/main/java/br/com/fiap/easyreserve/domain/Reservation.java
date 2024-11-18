package br.com.fiap.easyreserve.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Reservation {
        private Long id; // Identificador único
        private Long restaurantId; // ID do restaurante associado
        private String customerName; // Nome do cliente
        private String customerContact; // Contato do cliente (ex.: telefone ou e-mail)
        private LocalDateTime reservationDateTime; // Data e hora da reserva
        private int numberOfGuests; // Número de pessoas para a reserva
        private String status; // Status da reserva (ex.: CONFIRMED, CANCELED, COMPLETED)
}
