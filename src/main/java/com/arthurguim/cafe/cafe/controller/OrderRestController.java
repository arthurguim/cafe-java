package com.arthurguim.cafe.cafe.controller;

import java.util.ArrayList;
import java.util.List;

import com.arthurguim.cafe.cafe.exception.IngredientNotFoundException;
import com.arthurguim.cafe.cafe.model.Ingredient;
import com.arthurguim.cafe.cafe.model.Order;
import com.arthurguim.cafe.cafe.model.rest.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    @Autowired
    private IngredientValidator ingredientValidator;

    @Autowired
    private SalesController salesController;

    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public ResponseEntity<Order> customOrder(@RequestBody Request request) throws IngredientNotFoundException {

        List<Ingredient> ingredients = new ArrayList<>();

        // Interate through all ingredients in the request
        for (Ingredient ingredient : request.getIngredients()) {

            // Validate it and get the database ingredient
            Ingredient i = ingredientValidator.validateIngredient(ingredient.getName());

            // Set the quantity expliced in the request
            i.setQuantity(ingredient.getQuantity());

            ingredients.add(i);
        }

        // Create an order with the requests validated
        Order order = new Order(ingredients);

        // Set sales if the order is applicable
        order = salesController.applySaleIfValid(order);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
}