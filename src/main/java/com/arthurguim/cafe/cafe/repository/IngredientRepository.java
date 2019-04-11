package com.arthurguim.cafe.cafe.repository;

import java.util.List;

import com.arthurguim.cafe.model.Ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // Find the ingredient by it's name
    List<Ingredient> findByName(String name);
}