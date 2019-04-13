package com.arthurguim.cafe.cafe.controller;

import java.util.ArrayList;
import java.util.List;

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

    // TODO: fix exception
    @RequestMapping(value = "/custom", method = RequestMethod.POST)
    public ResponseEntity<Order> customOrder(@RequestBody Request request) throws Exception {

        List<Ingredient> ingredients = new ArrayList<>();
        for (Ingredient ingredient : request.getIngredients()) {
            Ingredient i = ingredientValidator.validateIngredient(ingredient.getName());
            i.setQuantity(ingredient.getQuantity());
            ingredients.add(i);
        }

        Order order = new Order(ingredients);
        order = salesController.applySaleIfValid(order);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
}