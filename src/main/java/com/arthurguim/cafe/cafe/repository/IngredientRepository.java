package com.arthurguim.cafe.cafe.repository;

import java.util.Optional;

import com.arthurguim.cafe.cafe.model.Ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // Find the ingredient by it's name
    Optional<Ingredient> findByName(String name);
}