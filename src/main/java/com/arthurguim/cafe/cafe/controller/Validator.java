package com.arthurguim.cafe.cafe.controller;

import java.util.Optional;

import com.arthurguim.cafe.cafe.exception.HamburguerNotFoundException;
import com.arthurguim.cafe.cafe.exception.IngredientNotFoundException;
import com.arthurguim.cafe.cafe.model.Hamburguer;
import com.arthurguim.cafe.cafe.model.Ingredient;
import com.arthurguim.cafe.cafe.repository.HamburguerRepository;
import com.arthurguim.cafe.cafe.repository.IngredientRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Validator {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private HamburguerRepository hamburguerRepository;

    public Ingredient validateIngredient(String name) throws IngredientNotFoundException {

        // Validate if the ingredient has a name
        if(name == null || name == StringUtils.EMPTY) {
            throw new IngredientNotFoundException("'name' is a mandatory");
        }

        // Get ingredient from repository given its name
        Optional<Ingredient> ingredientOpt = ingredientRepository.findByName(name);

        // Check if the ingredient exists
        if(!ingredientOpt.isPresent()) {
            throw new IngredientNotFoundException("Ingredient '" + name + "' was not found.");
        }

        return ingredientOpt.get();
    }

    public Hamburguer validateHamburguer(String name) throws HamburguerNotFoundException {

        // Validate if the hamburguer has a name
        if(name == null || name == StringUtils.EMPTY) {
            throw new HamburguerNotFoundException("'name' is a mandatory");
        }

        // Get hamburguer from repository given its name
        Optional<Hamburguer> hamburguerOpt = hamburguerRepository.findByName(name);

        // Check if the ingredient exists
        if(!hamburguerOpt.isPresent()) {
            throw new HamburguerNotFoundException("Hamburguer '" + name + "' was not found.");
        }

        return hamburguerOpt.get();
    }
}