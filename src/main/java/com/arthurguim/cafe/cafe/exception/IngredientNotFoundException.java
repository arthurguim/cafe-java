package com.arthurguim.cafe.cafe.exception;

public class IngredientNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3015060030257217693L;

    public IngredientNotFoundException() {
        super();
    }

    public IngredientNotFoundException(String message) {
        super(message);
    }
}