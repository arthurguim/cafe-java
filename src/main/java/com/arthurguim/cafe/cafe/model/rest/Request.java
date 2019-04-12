package com.arthurguim.cafe.cafe.model.rest;

import java.util.ArrayList;
import java.util.List;

import com.arthurguim.cafe.cafe.model.Ingredient;

import lombok.Getter;

@Getter
public class Request {
    private List<Ingredient> ingredients = new ArrayList<>();
}