package com.arthurguim.cafe.cafe.exception;

public class HamburguerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8055444083899408505L;

    public HamburguerNotFoundException() {
        super();
    }

    public HamburguerNotFoundException(String message) {
        super(message);
    }
}