package com.team404.bookstore.service;
import com.team404.bookstore.dao.HibernateConnection;
import com.team404.bookstore.dao.NewUnifiedDao;
import com.team404.bookstore.dao.UnifiedDao;
import com.team404.bookstore.entity.*;
import com.team404.bookstore.sslConfig.SSLConfig;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import javax.ws.rs.*;

/*service测试类
* 由于大部分情况下使用了Restlet Clinet或者直接浏览器输入url进行测试，所以该类中并未测试所有service方法*/
public class ServiceTest {
    public static void main(String args[]) throws Exception{

        SSLConfig sslConfig = new SSLConfig();
        Client client = sslConfig.ssl();

        UserEntity userEntity = new UserEntity();
        AddressEntity addressEntity = new AddressEntity();

        userEntity.setUsername("UpdateAPIandDAOtest@test.com");
        userEntity.setPassword("apidao");
        userEntity.setLastname("UpdateAPIdao_last");
        userEntity.setFirstname("UpdateAPIdao_first");
        userEntity.setId(24);

        Jsonb jsonb = JsonbBuilder.create();

        String s = jsonb.toJson(userEntity);
        System.out.println(s);

        MultivaluedMap<String, String> map = new MultivaluedHashMap<>();
        map.add("ClassName","UserEntity");
        map.add("Json", "{\"firstname\":\"APIdao_first\",\"id\":0,\"lastname\":\"APIdao_last\",\"password\":\"apidao\",\"username\":\"NewAPIandDAOtest@test.com\"}");
        System.out.println(map.get("Json").toString().replaceAll("[\\[\\]]", ""));

        Map<String,Object> map1 = new HashMap<String,Object>();
        List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(5);ids.add(10);
        map1.put("ids", ids);

        String ss =jsonb.toJson(map1);
        System.out.println(ss);
        ss = "[" + ss +"]";
        System.out.println(ss);

        Map<String, Object> map2 = new HashMap<>();
        map2 = jsonb.fromJson(ss.substring(1, ss.length()-2), map2.getClass());
        System.out.println(map2.get("ids").toString());

        Form form = new Form();
        form.param("firstResult", "0");
        form.param("maxResults", "0");


        MultivaluedMap<String, String> map3 = form.asMap();
        System.out.println(Integer.parseInt("[0]".replaceAll("[\\[\\]]","")));




//        String className = "com.team404.bookstore.entity." + map.get("ClassName").toString().replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+","");
//        Class<?> clz = Class.forName(className);
//        Object obj = clz.getDeclaredConstructor().newInstance();
//        System.out.println(obj.getClass().getName());
//        addressEntity.setPhone("8193182792");
//        addressEntity.setZip("K1S 5L6");
//        addressEntity.setStreet("200 LEES AVE");
//        addressEntity.setProvince("ON");
//        addressEntity.setCountry("Canada");
//
//
//        UserAddressCombine userAddressCombine = new UserAddressCombine(userEntity, addressEntity);

//        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
//
//        shoppingCartEntity.setUserid(20);
//        shoppingCartEntity.setQuantity(5);
//        shoppingCartEntity.setBookid("1627794247");

//        int userid = 20;
//        String json = jsonb.toJson(userid);
//        System.out.println(json);

//        OrderProcessAPI orderProcessAPI = new OrderProcessAPI();
//        System.out.println(orderProcessAPI.createOrder("1"));
//        ProductCatalogAPI productCatalogAPI = new ProductCatalogAPI();

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


//        UserEntity userEntity1 = new UserEntity();
//        userEntity1.setUsername("NewDAOtest@testT.com");
//        userEntity1.setPassword("daodaoT");
//        userEntity1.setLastname("DAO_lastT");
//        userEntity1.setFirstname("DAO_firstT");
//
//        NewUnifiedDao unifiedDao = new NewUnifiedDao();
////        System.out.println(unifiedDao.AddEntity(userEntity1));
//
//        UserEntity userEntity2 = (UserEntity)unifiedDao.GetEntityById(UserEntity.class, 23);
//        System.out.println(userEntity2.getUsername());
//
////        userEntity2.setUsername("UpdateDAO@test.comT");
////        System.out.println(unifiedDao.UpdateEntity(userEntity2));
//
//        System.out.println(unifiedDao.DeleteEntity(userEntity2));

        NewUnifiedDao newUnifiedDao = new NewUnifiedDao();
        String hql = "FROM UserEntity WHERE id in (:ids)";

        Map<String,Object> map4 = new HashMap<String,Object>();
        List<BigDecimal> ids1 = new ArrayList<>();

        ids1.add(new BigDecimal(1)); ids1.add(new BigDecimal(2));

        BigDecimal[] ids2 = new BigDecimal[2];
        ids2[0] = new BigDecimal(1);
        ids2[1] = new BigDecimal(2);
        map4.put("ids", ids2);
        AdminServiceAPI adminServiceAPI = new AdminServiceAPI();
        Map<String, Object> map5 = adminServiceAPI.Transform(map4);
        int[] arr = (int[]) map5.get("ids");
        System.out.println(arr.length);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }


//        List<UserEntity> userEntities = (List<UserEntity>) newUnifiedDao.GetDynamicList(hql, 0, 0, map4);
//        System.out.println(userEntities.size());
//        System.out.println(userEntities.get(0).getUsername()+ " " +
//                userEntities.get(1).getUsername()+ " " + userEntities.get(2).getUsername());
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

//    public static int AddTest(Object entity) {
//        Session session = HibernateConnection.getSession();
//        Transaction transaction = null;
//        int id = 0;
//
//        try {
//            transaction = session.beginTransaction();
//            session.save(entity);
//            transaction.commit();
//            try{
//                Method m = entity.getClass().getDeclaredMethod("getId");
//                id = (int) m.invoke(entity);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (HibernateException e) {
//            if (transaction!=null) transaction.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return id;
//    }
}
