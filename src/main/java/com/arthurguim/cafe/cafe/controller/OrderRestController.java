package com.arthurguim.cafe.cafe.controller;

import java.util.ArrayList;
import java.util.List;

import com.arthurguim.cafe.cafe.exception.HamburguerNotFoundException;
import com.arthurguim.cafe.cafe.exception.IngredientNotFoundException;
import com.arthurguim.cafe.cafe.model.Hamburguer;
import com.arthurguim.cafe.cafe.model.Ingredient;
import com.arthurguim.cafe.cafe.model.Order;
import com.arthurguim.cafe.cafe.model.rest.CustomRequest;
import com.arthurguim.cafe.cafe.model.rest.HamburguerRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    @Autowired
    private Validator validator;

    @Autowired
    private SalesController salesController;

    @RequestMapping(value = "/custom", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<Order> customOrder(@RequestBody CustomRequest request) throws IngredientNotFoundException {

        List<Ingredient> ingredients = new ArrayList<>();

        // Interate through all ingredients in the request
        for (Ingredient ingredient : request.getIngredients()) {

            // Validate it and get the database ingredient
            Ingredient i = validator.validateIngredient(ingredient.getName());

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

    @RequestMapping(value = "/hamburguer", method = RequestMethod.POST)
    public ResponseEntity<Order> hamburguerOrder(@RequestBody HamburguerRequest request) throws HamburguerNotFoundException {

        if(request.getHamburguer() == null) {
            throw new HamburguerNotFoundException("'hamburguer' is mandatory");
        }

        // Validate hamburguer
        Hamburguer hamburguer = validator.validateHamburguer(request.getHamburguer().getName());

        // Create hamburguer Order
        Order order = new Order(hamburguer.getIngredients(), hamburguer.getName());
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
}