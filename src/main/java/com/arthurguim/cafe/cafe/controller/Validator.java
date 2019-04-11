package com.arthurguim.cafe.cafe.controller;

import com.arthurguim.cafe.cafe.repository.HamburguerRepository;
import com.arthurguim.cafe.cafe.repository.IngredientRepository;
import com.arthurguim.cafe.model.Hamburguer;
import com.arthurguim.cafe.model.Ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private HamburguerRepository hamburguerRepository;

    public Ingredient validateIngredient(String name) {
        return ingredientRepository.findByName(name);
    }

    public Hamburguer validateHamburguer(String name) {
        return hamburguerRepository.findByName(name);
    }
}