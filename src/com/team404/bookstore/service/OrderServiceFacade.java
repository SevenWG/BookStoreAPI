package com.team404.bookstore.service;

import com.team404.bookstore.dao.AddressDao;
import com.team404.bookstore.dao.OrderBookDao;
import com.team404.bookstore.dao.OrderDao;
import com.team404.bookstore.dao.ShoppingCartDao;
import com.team404.bookstore.entity.OrderBookEntity;
import com.team404.bookstore.entity.OrdersEntity;
import com.team404.bookstore.entity.ShoppingCartEntity;

import java.sql.Timestamp;
import java.util.List;

/*
 * Implementation of Facade Pattern
 * */


public class OrderServiceFacade {

    public int OrderGnerator(int userid) {
        ShoppingCartDao shoppingCartDao = new ShoppingCartDao();
        AddressDao addressDao = new AddressDao();
        OrderDao orderDao = new OrderDao();


        TimeGeneratorInterface timeGenerator = new TimeGenerator();
        AmountCalculatorInterface amountCalculator = new AmountCalculator();
        PriceCalculatorInterface priceCalculator = new PriceCalculator();

        OrdersEntity orderEntity = new OrdersEntity();
        List<ShoppingCartEntity> list = shoppingCartDao.getListById(userid);

        orderEntity.setUserid(userid);
        orderEntity.setGenerationtime(timeGenerator.GetTimestampValue());
        orderEntity.setTotalprice(priceCalculator.CalculateTotalPrice(list));
        orderEntity.setAddressid(addressDao.getAddressByUid(userid).getId());
        orderEntity.setStatus("Processing");

        orderEntity = priceCalculator.setMorePriceValues(orderEntity, userid);

        orderEntity.setAmount(amountCalculator.CalculateAmount(list));

        int id = orderDao.AddOrder(orderEntity);

        createOrderBook(list, id);

        shoppingCartDao.DeleteShoppingItems(userid);

        return id;

    }

    public void createOrderBook(List<ShoppingCartEntity> list, int id) {
        OrderBookDao orderBookDao = new OrderBookDao();
        for(ShoppingCartEntity i : list) {
            OrderBookEntity orderBookEntity = new OrderBookEntity();
            orderBookEntity.setOrderid(id);
            orderBookEntity.setBookid(i.getBookid());
            orderBookEntity.setQuantity(i.getQuantity());
            orderBookDao.AddOrderBook(orderBookEntity);
        }
    }
}
