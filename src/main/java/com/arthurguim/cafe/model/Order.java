package com.arthurguim.cafe.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Order {

    // List of the ingredients in the order
    private List<Ingredient> ingredients = new ArrayList<>();

    // Sum of the price of the ingredients
    private Double totalPrice = .0;

    public Order(List<Ingredient> ingredients) {
        // Do the sum
        for (Ingredient ingredient : ingredients) {
            this.totalPrice += ingredient.getPrice();
        }

        this.ingredients = ingredients;
    }
}