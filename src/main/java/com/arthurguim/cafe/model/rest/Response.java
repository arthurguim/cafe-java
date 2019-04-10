package com.arthurguim.cafe.model.rest;

import com.arthurguim.cafe.model.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {
    private Order order;
    private boolean onSale;
    private String saleName;
}