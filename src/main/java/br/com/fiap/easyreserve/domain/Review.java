package br.com.fiap.easyreserve.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Review {
    private Long id;
    private Long restaurantId;
    private Long userId;
    private String comments;
    private int rating;
}
