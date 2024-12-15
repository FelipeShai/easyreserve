package br.com.fiap.easyreserve.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Restaurant {
    private Long id;
    private String name;
    private String location;
    private String cuisineType;
    private String openingHours;
    private int capacity;
}
