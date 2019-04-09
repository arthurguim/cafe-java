package com.arthurguim.cafe.model.rest;

import com.arthurguim.cafe.model.Ingredient;

import lombok.Getter;

@Getter
public class CafeRequestBody {
    private Ingredient ingredients[];
}