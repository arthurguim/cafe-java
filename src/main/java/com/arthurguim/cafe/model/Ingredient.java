package com.arthurguim.cafe.model;

import lombok.Getter;

@Getter
public class Ingredient {

    // Ingredient name
    private String name;

    // Ingredient price
    private Double price;

    public Ingredient(String name, Double price) {
        this.name = name;
        this.price = price;
    }
}