package com.team404.bookstore.service;
import com.team404.bookstore.dao.HibernateConnection;
import com.team404.bookstore.entity.*;
import com.team404.bookstore.sslConfig.SSLConfig;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.lang.reflect.Method;

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


        UserAddressCombine userAddressCombine = new UserAddressCombine(userEntity, addressEntity);

        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();

        shoppingCartEntity.setUserid(20);
        shoppingCartEntity.setQuantity(5);
        shoppingCartEntity.setBookid("1627794247");

//        int userid = 20;
//        String json = jsonb.toJson(userid);
//        System.out.println(json);

        OrderProcessAPI orderProcessAPI = new OrderProcessAPI();
//        System.out.println(orderProcessAPI.createOrder("1"));
        ProductCatalogAPI productCatalogAPI = new ProductCatalogAPI();

//        Response response = productCatalogAPI.getProductInfo("1118008189");
//
//        System.out.println(response.getStatus()); //获取状态码
//        System.out.println(response.getEntity().toString());//实体为json类型
//
//        Jsonb jsonb = JsonbBuilder.create();
//        BookEntity bookEntity = jsonb.fromJson(response.getEntity().toString(),
//                BookEntity.class);//json转book实体
//
//        System.out.println(bookEntity.getTitle());


        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUsername("NewAPItest@test.com");
        userEntity1.setPassword("apiapi");
        userEntity1.setLastname("API_last");
        userEntity1.setFirstname("API_first");

        System.out.println(AddTest(userEntity1));
    }

    public static Object Test(String classname, int id) {
        String str = "com.team404.bookstore.entity.UserEntity";
        Object obj = null;

        try {
            Class<?> clz = Class.forName(str);
            Session session = HibernateConnection.getSession();
            Transaction transaction = null;;

            transaction = session.beginTransaction();
            obj = session.get(clz, 1);
            transaction.commit();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static int AddTest(Object entity) {
        Session session = HibernateConnection.getSession();

        Transaction transaction = null;
        int id = 0;
        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            try{
                Method m = entity.getClass().getDeclaredMethod("getId");
                id = (int) m.invoke(entity);
            }catch (Exception e) {
                e.printStackTrace();
            }
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }
}
