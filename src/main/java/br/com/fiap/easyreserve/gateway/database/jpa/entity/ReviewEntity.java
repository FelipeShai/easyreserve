package br.com.fiap.easyreserve.gateway.database.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único no banco de dados

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId; // ID do restaurante associado (FK)

    @Column(name = "customer_name", nullable = false)
    private String customerName; // Nome do cliente que avaliou

    private String comment; // Comentário sobre a experiência

    private int rating; // Nota (1 a 5)
}
