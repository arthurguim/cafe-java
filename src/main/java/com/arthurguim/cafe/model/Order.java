package com.arthurguim.cafe.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private List<String> ingredients = new ArrayList<>();
    private Double totalPrice = .0;

    public Order(Ingredient ingredients[]) {
        for (Ingredient ingredient : ingredients) {
            this.ingredients.add(ingredient.getName());
            this.totalPrice += ingredient.getPrice();
        }
    }
}