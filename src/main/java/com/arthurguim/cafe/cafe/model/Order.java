package com.arthurguim.cafe.cafe.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

    // List of the ingredients in the order
    private List<Ingredient> ingredients = new ArrayList<>();

    // Sum of the price of the ingredients
    private Double totalPrice = .0;

    // Name of the sale(s)
    private List<String> saleName = new ArrayList<>();

    private Double discount = .0;

    public Order(List<Ingredient> ingredients) {

        for (Ingredient ingredient : ingredients) {
            Double ingredientPrice = ingredient.getPrice() * ingredient.getQuantity();
            this.totalPrice += ingredientPrice;
        }

        this.ingredients = ingredients;
    }

    public void includeSaleName(String name) {
        this.saleName.add(name);
    }
}