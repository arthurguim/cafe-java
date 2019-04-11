package com.arthurguim.cafe.model.rest;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Request {
    private List<String> ingredients = new ArrayList<>();
}