package com.team404.bookstore.service;
import com.team404.bookstore.entity.AddressEntity;
import com.team404.bookstore.entity.ShoppingCartEntity;
import com.team404.bookstore.entity.UserAddressCombine;
import com.team404.bookstore.entity.UserEntity;
import com.team404.bookstore.sslConfig.SSLConfig;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;

/*service测试类
* 由于大部分情况下使用了Restlet Clinet或者直接浏览器输入url进行测试，所以该类中并未测试所有service方法*/
public class ServiceTest {
    public static void main(String args[]) throws Exception{

        SSLConfig sslConfig = new SSLConfig();
        Client client = sslConfig.ssl();

        UserEntity userEntity = new UserEntity();
        AddressEntity addressEntity = new AddressEntity();

        userEntity.setUsername("APItest@test.com");
        userEntity.setPassword("apiapi");
        userEntity.setLastname("API_last");
        userEntity.setFirstname("API_first");

        addressEntity.setPhone("8193182792");
        addressEntity.setZip("K1S 5L6");
        addressEntity.setStreet("200 LEES AVE");
        addressEntity.setProvince("ON");
        addressEntity.setCountry("Canada");
        Jsonb jsonb = JsonbBuilder.create();

        UserAddressCombine userAddressCombine = new UserAddressCombine(userEntity, addressEntity);

        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();

        shoppingCartEntity.setUserid(20);
        shoppingCartEntity.setQuantity(5);
        shoppingCartEntity.setBookid("1627794247");

        int userid = 20;
        String json = jsonb.toJson(userid);
        System.out.println(json);

        OrderProcessAPI orderProcessAPI = new OrderProcessAPI();
//        System.out.println(orderProcessAPI.createOrder("1"));
        ProductCatalogAPI productCatalogAPI = new ProductCatalogAPI();
        System.out.println(productCatalogAPI.getProductInfo("1") == null);


    }
}
