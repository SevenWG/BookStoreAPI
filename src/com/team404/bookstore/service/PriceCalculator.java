package com.team404.bookstore.service;

import com.team404.bookstore.dao.AddressDao;
import com.team404.bookstore.dao.BookDao;
import com.team404.bookstore.entity.BookEntity;
import com.team404.bookstore.entity.OrdersEntity;
import com.team404.bookstore.entity.ShoppingCartEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * Implementation of Facade Pattern
 * */
public class PriceCalculator implements PriceCalculatorInterface {

    private BookDao bookDao;
    private AddressDao addressDao;

    public float CalculateTotalPrice(List<ShoppingCartEntity> list) {
        bookDao = new BookDao();
        float totalPrice = 0;

        for(ShoppingCartEntity i : list) {
            BookEntity bookEntity = bookDao.getEntityById(Integer.valueOf(i.getBookid()));
            totalPrice += bookEntity.getPrice()*i.getQuantity();
        }
        return totalPrice;
    }

    public OrdersEntity setMorePriceValues(OrdersEntity orderEntity, int userid) {
        addressDao = new AddressDao();

        if(addressDao.getAddressByUid(userid).getProvince().equals("ON")) {
            orderEntity.setShipping(5);
            orderEntity.setTax(orderEntity.getTotalprice()*0.13);
            orderEntity.setAftertaxprice(orderEntity.getTotalprice()*1.13+orderEntity.getShipping());
        }else {
            orderEntity.setShipping(8);
            orderEntity.setTax(orderEntity.getTotalprice()*0.08);
            orderEntity.setAftertaxprice(orderEntity.getTotalprice()*1.08+orderEntity.getShipping());
        }

        return orderEntity;

    }

}
