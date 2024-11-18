package br.com.fiap.easyreserve.gateway.database.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único no banco de dados

    private String name; // Nome do restaurante

    private String location; // Localização (endereço ou cidade)

    @Column(name = "cuisine_type")
    private String cuisineType; // Tipo de cozinha (ex.: italiana, japonesa)

    @Column(name = "opening_hours")
    private String openingHours; // Horários de funcionamento (ex.: "10:00-22:00")

    private int capacity; // Capacidade máxima de clientes
}
