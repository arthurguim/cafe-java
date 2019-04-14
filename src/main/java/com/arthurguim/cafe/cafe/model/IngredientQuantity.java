package com.arthurguim.cafe.cafe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "INGREDIENT_QUANTITY")
public class IngredientQuantity {
    
    // Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    // The hamburguer
    @ManyToOne
    @JoinColumn(name = "HAMBURGUER_ID")
    private Hamburguer hamburguer;

    // The ingredient
    @ManyToOne
    @JoinColumn(name = "INGREDIENT_ID")
    private Ingredient ingredient;
    
    // How much of its ingredient must have this hamburguer
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;
}