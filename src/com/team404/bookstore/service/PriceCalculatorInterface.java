package com.team404.bookstore.service;

import com.team404.bookstore.entity.OrdersEntity;
import com.team404.bookstore.entity.ShoppingCartEntity;

import java.util.List;
/*
 * Implementation of Facade Pattern
 * */
public interface PriceCalculatorInterface {
    public float CalculateTotalPrice(List<ShoppingCartEntity> list);
    public OrdersEntity setMorePriceValues(OrdersEntity orderEntity, int userid);
}
