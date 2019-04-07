package com.arthurguim.cafe.model;

import lombok.Getter;

@Getter
public class Ingredients {
	private IngredientType type;
    private Double price;

    public Ingredients(IngredientType type) {
        switch (type) {
            case LETTUCE:
                this.price = Constants.LETTUCE_PRICE;
                break;
            case BACON:
                this.price = Constants.BACON_PRICE;
                break;
            case HAMBURGUER:
                this.price = Constants.HAMBURGUER_PRICE;
                break;
            case EGG:
                this.price = Constants.EGG_PRICE;
                break;
            case CHEESE:
                this.price = Constants.CHEESE_PRICE;
                break;
            default:
                // TODO: throw exception
                this.price = 0.;
                break;
        }
    }
}