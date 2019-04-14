package com.arthurguim.cafe.cafe.controller;

import java.util.Optional;

import com.arthurguim.cafe.cafe.exception.IngredientNotFoundException;
import com.arthurguim.cafe.cafe.model.Hamburguer;
import com.arthurguim.cafe.cafe.model.Ingredient;
import com.arthurguim.cafe.cafe.repository.HamburguerRepository;
import com.arthurguim.cafe.cafe.repository.IngredientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientValidator {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private HamburguerRepository hamburguerRepository;

    public Ingredient validateIngredient(String name) throws IngredientNotFoundException {
        // Get ingredient from repository given its name
        Optional<Ingredient> ingredientOpt = ingredientRepository.findByName(name);

        // Check if the ingredient exists
        if(!ingredientOpt.isPresent()) {
            throw new IngredientNotFoundException("Ingredient '" + name + "' was not found.");
        }

        return ingredientOpt.get();
    }

    public Hamburguer validateHamburguer(String name) {
        return hamburguerRepository.findByName(name);
    }
}