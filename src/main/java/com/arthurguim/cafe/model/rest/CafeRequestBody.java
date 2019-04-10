package com.arthurguim.cafe.model.rest;

import java.util.ArrayList;
import java.util.List;

import com.arthurguim.cafe.model.Ingredient;

import lombok.Getter;

@Getter
public class CafeRequestBody {
    private List<Ingredient> ingredients = new ArrayList<>();
}