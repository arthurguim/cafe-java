package com.arthurguim.cafe.cafe.model.sales;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "QUANTITATIVE_SALE")
public class QuantitativeSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "INCLUDANT_INGREDIENT_ID", nullable = false)
    private Long includantIngredientId;
    
    @Column(name = "EXCLUDANT_INGREDIENT_ID", nullable = false)
    private Long excludantIngredientId;

    @Column(name = "SALE_PERCENTAGE", nullable = false)
    private Double salePercentage;
}