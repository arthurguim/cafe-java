package com.arthurguim.cafe.cafe.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Order {

    // List of the ingredients in the order
    private List<Ingredient> ingredients = new ArrayList<>();

    // Sum of the price of the ingredients
    private Double totalPrice = .0;

    // Name of the sale
    private String saleName;

    public Order(List<Ingredient> ingredients) throws Exception {

        for (Ingredient ingredient : ingredients) {
            Double ingredientPrice = ingredient.getPrice() * ingredient.getQuantity();
            this.totalPrice += ingredientPrice;
        }

        this.ingredients = ingredients;
    }
}