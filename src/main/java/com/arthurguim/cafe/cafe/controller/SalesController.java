package com.arthurguim.cafe.cafe.controller;

import java.util.List;

import com.arthurguim.cafe.cafe.model.Ingredient;
import com.arthurguim.cafe.cafe.model.Order;
import com.arthurguim.cafe.cafe.model.sales.ProportionalSale;
import com.arthurguim.cafe.cafe.model.sales.QuantitativeSale;
import com.arthurguim.cafe.cafe.repository.ProportionalSaleRepository;
import com.arthurguim.cafe.cafe.repository.QuantitativeSaleRepository;

import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesController {

    @Autowired
    private ProportionalSaleRepository proportionalSaleRepository;

    @Autowired
    private QuantitativeSaleRepository quantitativeSaleRepository;

    private List<ProportionalSale> proportionalSales;
    private List<QuantitativeSale> quantitativeSales;

    public Order applySaleIfValid(Order order) {
        this.proportionalSales = proportionalSaleRepository.findAll();
        this.quantitativeSales = quantitativeSaleRepository.findAll();

        order = applyQuantitativeSale(order);
        return applyProportionalSale(order);
    }

    private Order applyProportionalSale(Order order) {

        // Interate through all proportional sales
        for (ProportionalSale proportionalSale : proportionalSales) {

            // Interate through all ingredients
            order.getIngredients().forEach(ingredient -> {
                
                // Indentify ingredient that has proportional sale
                if(ingredient.getId() == proportionalSale.getIngredientId()) {

                    // Ingredient quantity and price
                    int ingredientQuantity = ingredient.getQuantity();
                    Double ingredientPrice = ingredient.getPrice();

                    // Sale proportion
                    int proportion = proportionalSale.getProportion();

                    // Order original price and discount
                    Double orderTotalPrice = order.getTotalPrice();
                    Double orderDiscount = order.getDiscount();

                    // Verify if there's the right quantity
                    if(ingredientQuantity / proportion > 0) {

                        // Get the sale portion
                        int aux = ingredientQuantity / proportion;

                        // Get new ingredient price
                        Double salePrice = (ingredientQuantity - aux) * ingredientPrice;

                        // Get discount
                        Double discount = (ingredientPrice * ingredientQuantity) - salePrice;
                        discount = Precision.round(discount, 2);

                        // Set order new price
                        Double orderNewPrice = orderTotalPrice - discount;
                        orderNewPrice = Precision.round(orderNewPrice, 2);
                        // Update order
                        order.setTotalPrice(orderNewPrice);
                        order.includeSaleName(proportionalSale.getName());
                        order.setDiscount(orderDiscount + discount);
                    }
                }
            });
        }

        return order;
    }

    private Order applyQuantitativeSale(Order order) {
        boolean hasIncludantIngredient = false;
        boolean hasExcludantIngredient = false;
        Double salePorcentage = .0;

        // Interate through all quantity sales
        for (QuantitativeSale quantitativeSale : quantitativeSales) {

            // Interate through all ingredients in the order
            for (Ingredient ingredient : order.getIngredients()) {

                // Check if the ingredient is an includant ingredient
                if(ingredient.getId() == quantitativeSale.getIncludantIngredientId()) {
                    hasIncludantIngredient = true;
                    continue;
                }

                // Check if the ingredient is an excludant ingredient
                if(ingredient.getId() == quantitativeSale.getExcludantIngredientId()) {
                    hasExcludantIngredient = true;
                }
            }

            // Check if the sale is applicable
            if(hasIncludantIngredient && !hasExcludantIngredient) {
                salePorcentage += quantitativeSale.getSalePercentage();
                order.includeSaleName(quantitativeSale.getName());
            }
        }

        // If there's discount to apply
        if(salePorcentage != 0) {
            // Apply discount
            Double orderOriginalPrice = order.getTotalPrice();
            Double orderDiscount = order.getDiscount();

            // Set price with discount
            Double orderNewPrice = orderOriginalPrice * (1 - salePorcentage / 100);
            Double discount = orderOriginalPrice - orderNewPrice;
            orderNewPrice = Precision.round(orderNewPrice, 2);
            discount = Precision.round(discount, 2);

            // Update order
            order.setTotalPrice(orderNewPrice);
            order.setDiscount(orderDiscount + discount);
        }

        return order;
    }
}