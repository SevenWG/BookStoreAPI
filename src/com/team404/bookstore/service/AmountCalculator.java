package com.team404.bookstore.service;

import com.team404.bookstore.entity.ShoppingCartEntity;

import java.util.List;

/*
 * Implementation of Facade Pattern
 * */

public class AmountCalculator implements AmountCalculatorInterface {

    public int CalculateAmount(List list) {
        int amount = 0;
        for(int i = 0; i < list.size(); i++){
            ShoppingCartEntity shoppingCartEntity = (ShoppingCartEntity)list.get(i);
            amount += shoppingCartEntity.getQuantity();
        }
        return amount;
    }
}