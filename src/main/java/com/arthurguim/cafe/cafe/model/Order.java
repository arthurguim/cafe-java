package com.arthurguim.cafe.cafe.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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

    // The discount applied in this order
    private Double discount = .0;

    // If it is a custom hamburguer
    private boolean isCustomHamburguer;

    // Hamburguer name
    private String hamburguerName = StringUtils.EMPTY;

    // Constructor for custom hamburguer
    public Order(List<Ingredient> ingredients) {

        addIngredients(ingredients);
        this.isCustomHamburguer = false;
        this.hamburguerName = StringUtils.EMPTY;
    }

    // Constructor for hamburguer
    public Order(List<Ingredient> ingredients, String hamburguerName) {
        addIngredients(ingredients);
        this.isCustomHamburguer = true;
        this.hamburguerName = hamburguerName;
    }

    // Include sale name on list
    public void includeSaleName(String name) {
        this.saleName.add(name);
    }

    // Add ingredients to this order
    private void addIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            Double ingredientPrice = ingredient.getPrice() * ingredient.getQuantity();
            this.totalPrice += ingredientPrice;
        }

        this.ingredients = ingredients;
    }
}