package br.com.fiap.easyreserve.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Review {
    private Long id; // Identificador único
    private Long restaurantId; // ID do restaurante associado
    private String customerName; // Nome do cliente que avaliou
    private String comment; // Comentário sobre a experiência
    private int rating; // Nota (1 a 5)
}
