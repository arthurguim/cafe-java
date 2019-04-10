package com.arthurguim.cafe.model;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class Ingredient {

    // Ingredient name
    private String name;

    // Ingredient price
    private Double price;

    // TODO: remove this list
    private final List<String> VALID_INGREDIENTS = Arrays.asList("hamburguer", "cheese", "egg", "lettuce", "bacon");

    public Ingredient(String name, Double price) throws Exception {
        // Check if the name of the ingredient is registered
        if (!VALID_INGREDIENTS.contains(name)) {
            // TODO: throw right exception if ingredient is not valid
            throw new Exception("Not valid");
        }

        this.name = name;
        this.price = price;
    }
}