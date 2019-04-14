package com.arthurguim.cafe.cafe.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"ingredientQuantitys"})
@Entity
@Table(name = "HAMBURGUERS")
public class Hamburguer {

    // ID for the database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long Id;

    // Hamburguer name
    @Column(name = "NAME", nullable = false)
    private String name;

    // Hamburguer ingredients
    @OneToMany(mappedBy = "hamburguer")
    private List<IngredientQuantity> ingredientQuantitys;

    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();

        for (IngredientQuantity ingredientQuantity : this.ingredientQuantitys) {
            Ingredient ingredient = ingredientQuantity.getIngredient();
            ingredient.setQuantity(ingredientQuantity.getQuantity());
            ingredients.add(ingredient);
        }

        return ingredients;
    }
}