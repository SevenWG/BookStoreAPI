package com.team404.bookstore.service;

import com.team404.bookstore.dao.*;
import com.team404.bookstore.entity.*;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/OrderProcess")
public class OrderProcessAPI {
    private UserDao userDao;
    private AddressDao addressDao;
    private ShoppingCartDao shoppingCartDao;
    private CountDao countDao;
    private OrderDao orderDao;
    private OrderBookDao orderBookDao;

    private DaoFactoryImpl daoFactory = DaoFactoryImpl.SingleDaoFactory();
    private OrderServiceFacade orderServiceFacade;

    @GET
    @Path("/GetUserByAccount/{userAccount}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity GetUserByAccount(@PathParam("userAccount") String userAccount){

        userDao = new UserDao();

        UserEntity userEntity = userDao.GetUserbyAccount(userAccount);

        return  userEntity;
    }

    @POST
    @Path("/CreateAccount")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    /*Submit Function*/
    /*不同于之前项目里的注册方法，由于不熟悉POST方法，不知道如何通过Jersey传递对象，所以在接收前先提前将对象转换为了json字符串，该方法接收到数据后再转换回对象
    * Different from the registration method in the previous project, because I am not familiar with the POST method, I don't know how to pass the object through Jersey.
    * So I convert the object to json string before receiving it. The method receives the data and then converts it back to the object.*/
    /*Reference:https://www.ibm.com/developerworks/cn/java/j-javaee8-json-binding-1/index.html?ca=drs-&utm_source=tuicool&utm_medium=referral*/
    /*该方法目前无法用测试类测试，使用的测试工具为Restlet Client
    * This method cannot currently be tested with a test class. The test tool used is the Restlet Client.*/
    public boolean CreateAccount(String json) {
        boolean flag = true;

        userDao = new UserDao();
        addressDao = new AddressDao();

        Jsonb jsonb = JsonbBuilder.create();

        UserAddressCombine userAddressCombine = jsonb.fromJson(json, UserAddressCombine.class);

        UserEntity userEntity = userAddressCombine.getUserEntity();
        AddressEntity addressEntity = userAddressCombine.getAddressEntity();

        UserEntity userEntity1 = userDao.GetUserbyAccount(userEntity.getUsername());

        if(userEntity1 != null) {
            flag = false;
        }
        else {
            int id = userDao.AddUser(userEntity);
            addressEntity.setUserid(id);
            System.out.print(addressEntity.getUserid());
            addressDao.AddAddress(addressEntity);
        }
        return  flag;
    }

    @POST
    @Path("/getAccount")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean getAccount(String json) {
        boolean flag = true;
        userDao = new UserDao();

        Jsonb jsonb = JsonbBuilder.create();
        UserEntity userEntity = jsonb.fromJson(json, UserEntity.class);
        UserEntity userEntity1 = userDao.GetUserbyAccount(userEntity.getUsername());

        if(userEntity1 != null){
            if(userEntity.getPassword().equals(userEntity1.getPassword())) {
                flag = true;
            } else {
                flag = false;
            }
        } else {

            flag = false;
        }
        return flag;
    }

    @GET
    @Path("/getUserinfo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity getUserinfo(@PathParam("id") int id) {
        userDao = new UserDao();

        UserEntity userEntity = userDao.GetUserById(id);

        return userEntity;
    }

    @GET
    @Path("/getAddressinfo/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public AddressEntity getAddressinfo(@PathParam("userid") int userid) {
        addressDao = new AddressDao();

        AddressEntity addressEntity = addressDao.getAddressByUid(userid);

        return addressEntity;
    }

    @POST
    @Path("/AddItemtoCart")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean AddItemtoCart(String json) {

        shoppingCartDao = new ShoppingCartDao();

        Jsonb jsonb = JsonbBuilder.create();
        ShoppingCartEntity shoppingCartEntity = jsonb.fromJson(json, ShoppingCartEntity.class);

        if(shoppingCartDao.GetCartItem(shoppingCartEntity.getUserid(),
                shoppingCartEntity.getBookid()) == null) {
            return shoppingCartDao.AddShoppingCart(shoppingCartEntity);
        }

        else {
            return shoppingCartDao.UpdateItemQuantity(shoppingCartEntity);
        }

    }

    @GET
    @Path("/DisplayShoppingCart/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    /*Display customers' shopping cart contents*/
    /*
     * Implementation of Factory Pattern
     * */
    /*java.lang.NullPointerException
	com.team404.bookstore.service.OrderProcessAPI.DisplayShoppingCart(OrderProcessAPI.java:170)
	原因：忘记实例化shoppingCartDao或者是忘记实例化daoFactory*/

    public List<ShoppingCartEntity> DisplayShoppingCart(@PathParam("userid") int userid) {

//        shoppingCartDao = new ShoppingCartDao();
//        List<ShoppingCartEntity> list = shoppingCartDao.getListById(userid);

        List<ShoppingCartEntity> list = (List<ShoppingCartEntity>)daoFactory.
                ListSomethingById("ShoppingCartDao", "getListById", userid);

        return  list;
    }

    @DELETE
    @Path("/DeleteSingleItem/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    /*Delete single item in shopping cart*/
    public boolean DeleteSingleItem(@PathParam("id") int id) {
        shoppingCartDao = new ShoppingCartDao();

        return shoppingCartDao.DeleteShoppingItemById(id);
    }

    @POST
    @Path("/createOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    /*获取自增id的配置！！！
     * 在实体类的xml文件中的id的配置行中添加<generator class="identity" />
     * */
    public int createOrder(String json) {

        Jsonb jsonb = JsonbBuilder.create();
        int userid = jsonb.fromJson(json, int.class);

        orderServiceFacade = new OrderServiceFacade();

        int id = orderServiceFacade.OrderGnerator(userid);

        return id;
    }

    @GET
    @Path("/confirmOrder/{orderid}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean confirmOrder(@PathParam("orderid") int orderid) {
        countDao = new CountDao();
        orderDao = new OrderDao();
        boolean flag = true;

        if(countDao.getCount().getCounts() % 5 == 0 && countDao.getCount().getCounts() >= 5) {
            countDao.CountUpdate();
            flag = false;
            orderDao.UpdateOrderStatus(orderid, flag);
        }else {
            countDao.CountUpdate();
            flag = true;
            orderDao.UpdateOrderStatus(orderid, flag);
        }
        return flag;
    }

    @GET
    @Path("/DisplayMyOrder/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrdersEntity> DisplayMyOrder (@PathParam("userid") int userid) {

        List<OrdersEntity> list =
                (List<OrdersEntity>)daoFactory.
                        ListSomethingById("OrderDao", "getListById", userid);

        return  list;
    }

    @GET
    @Path("/GetOrderBooks/{orderid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderBookEntity> GetOrderBooks (@PathParam("orderid") int orderid) {
        orderBookDao = new OrderBookDao();

        return  orderBookDao.GetOrderBookByOid(orderid);
    }
}
