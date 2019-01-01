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
    public List<BookEntity> getProductList() {

        List<BookEntity> list = null;

        list = (List<BookEntity>)daoFactory.ListSomethingById("BookDao", "getListById", 0);

        return list;
    }

    /*gets the list of products for a specific category*/
    /*
     * Implementation of Factory Pattern
     * */
    @GET
    @Path("/getProductList/{categoryid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookEntity> getProductList(@PathParam("categoryid") int categoryid) {

        List<BookEntity> list = null;

        list = (List<BookEntity>)daoFactory.
                ListSomethingById("BookDao", "getListById", categoryid);

        return  list;
    }

    /* gets the detailed product information for a product.*/
    /*
     * Implementation of Factory Pattern
     * */
    @GET
    @Path("/getProductInfo/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public BookEntity getProductInfo(@PathParam("id") String id) {
        BookEntity bookEntity = null;
        boolean flag = false;

        int id_int = Integer.parseInt(id.trim());

        bookEntity = (BookEntity)daoFactory.getEntityById("BookDao", "getEntityById", id_int);

        return  bookEntity;
    }

    @GET
    @Path("/getCategory/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CategoryEntity getCategory(@PathParam("id") int id) {
        categoryDao = new CategoryDao();

        CategoryEntity categoryEntity = categoryDao.getCategoryById(id);

        return categoryEntity;
    }
}
