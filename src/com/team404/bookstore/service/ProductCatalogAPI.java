package com.team404.bookstore.service;

import com.team404.bookstore.dao.BookDao;
import com.team404.bookstore.dao.CategoryDao;
import com.team404.bookstore.dao.DaoFactoryImpl;
import com.team404.bookstore.entity.BookEntity;
import com.team404.bookstore.entity.CategoryEntity;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ProductCatalog")
public class ProductCatalogAPI {

    private CategoryDao categoryDao;
    private BookDao bookDao;
    private DaoFactoryImpl daoFactory = DaoFactoryImpl.SingleDaoFactory();
    private static Jsonb jsonb = JsonbBuilder.create();

    @GET
    @Path("/getCategoryList")
    @Produces(MediaType.APPLICATION_JSON)
    /* gets the list of product categories for the store */
    public Response getCategoryList() {
        categoryDao = new CategoryDao();

        List<CategoryEntity> list = categoryDao.ListCategory();

        return Response.status(Response.Status.OK).entity(jsonb.toJson(list)).build();
    }

    @GET
    @Path("/getProductList")
    @Produces(MediaType.APPLICATION_JSON)
    /*gets the list of products*/
    /*
     * Implementation of Factory Pattern
     * */
    public Response getProductList() {

        List<BookEntity> list = null;

        list = (List<BookEntity>)daoFactory.ListSomethingById("BookDao", "getListById", 0);

        return Response.status(Response.Status.OK).entity(jsonb.toJson(list)).build();
    }

    /*gets the list of products for a specific category*/
    /*
     * Implementation of Factory Pattern
     * */
    /*
    When the size of list is 0(Wrong Category or no book in this catefory)
    return HTTP 400 + wrong info message
    otherwise, return 200 + list
    * */

    @GET
    @Path("/getProductList/{categoryid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductList(@PathParam("categoryid") int categoryid) {

        List<BookEntity> list = null;

        list = (List<BookEntity>)daoFactory.
                ListSomethingById("BookDao", "getListById", categoryid);

        if(list.size() == 0) {
            String erroMessage = "Wrong Category or No book in this Category!";
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(erroMessage)).build();
        }
        else {
            return Response.status(Response.Status.OK).entity(jsonb.toJson(list)).build();
        }
    }

    /* gets the detailed product information for a product.*/
    /*
     * Implementation of Factory Pattern
     * */
    /*
    When the book entity is equals to null ( == null, do not use .equals())
    return HTTP 400 + wrong info message
    otherwise, return 200 + book entity
    * */
    @GET
    @Path("/getProductInfo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductInfo(@PathParam("id") String id) {
        BookEntity bookEntity = null;
        boolean flag = false;

        int id_int = Integer.parseInt(id.trim());

        bookEntity = (BookEntity)daoFactory.getEntityById("BookDao", "getEntityById", id_int);

        if(bookEntity == null) {
            String errorMessage = "Wrong Book ID!";
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(errorMessage)).build();
        }
        else {
            return Response.status(Response.Status.OK).entity(jsonb.toJson(bookEntity)).build();
        }
    }

    /* gets the detailed information for a category.*/
    /*
    When the category entity is equals to null ( == null, do not use .equals())
    return HTTP 400 + wrong info message
    otherwise, return 200 + category entity
    * */
    @GET
    @Path("/getCategory/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("id") int id) {
        categoryDao = new CategoryDao();

        CategoryEntity categoryEntity = categoryDao.getCategoryById(id);

        if(categoryEntity == null) {
            String errorMessage = "Wrong Category ID!";
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(errorMessage)).build();
        }
        else {
            return Response.status(Response.Status.OK).entity(categoryEntity).build();
        }

    }
}
