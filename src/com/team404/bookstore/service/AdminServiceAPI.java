package com.team404.bookstore.service;

import com.team404.bookstore.dao.NewUnifiedDao;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.Map;

@Path("/AdminService")
public class AdminServiceAPI<T> {

    private NewUnifiedDao newUnifiedDao = new NewUnifiedDao();
    private static Jsonb jsonb = JsonbBuilder.create();

    @POST
    @Path("/AddEntity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response AddEntity(Form form) {
        Response response = null;
        MultivaluedMap<String, String> map = form.asMap();
        int id = 0;

        String className = "com.team404.bookstore.entity." +
                map.get("ClassName").toString().replaceAll("[\\[\\]]","");

        String json = map.get("Json").toString().replaceAll("[\\[\\]]", "");

        try {
            Class<?> clz = Class.forName(className);
            Object entity = jsonb.fromJson(json, clz);
            id = newUnifiedDao.AddEntity(entity);
        }catch (Exception e) {
            e.printStackTrace();
        }

        if(id != 0) {
            response = Response.status(Response.Status.OK).entity(jsonb.toJson(id)).build();
            return response;
        }
        else {
            String errorMessage = "Add Action Failed!";
            response = Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(errorMessage)).build();
            return  response;
        }
    }

    @POST
    @Path("/DeleteEntity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response DeleteEntity(Form form) {
        Response response = null;
        MultivaluedMap<String, String> map = form.asMap();
        boolean flag = true;

        String className = "com.team404.bookstore.entity." +
                map.get("ClassName").toString().replaceAll("[\\[\\]]","");

        String json = map.get("Json").toString().replaceAll("[\\[\\]]", "");

        try {
            Class<?> clz = Class.forName(className);
            Object entity = jsonb.fromJson(json, clz);
            flag = newUnifiedDao.DeleteEntity(entity);
            System.out.println(flag);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(flag) {
            response = Response.status(Response.Status.OK).entity(jsonb.toJson(flag)).build();
            return response;
        }
        else {
            String errorMessage = "Delte Action Failed!";
            response = Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(errorMessage)).build();
            return  response;
        }
    }

    @POST
    @Path("/GetEntity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetEntity(Form form) {
        Response response = null;
        MultivaluedMap<String, String> map = form.asMap();
        Object object = null;

        String className = "com.team404.bookstore.entity." +
                map.get("ClassName").toString().replaceAll("[\\[\\]]","");

        String json = map.get("id").toString().replaceAll("[\\[\\]]", "");
        int id = Integer.parseInt(json);

        try {
            Class<?> clz = Class.forName(className);
            object = newUnifiedDao.GetEntityById(clz, id);
        }catch (Exception e) {
            e.printStackTrace();
        }

        if(object != null) {
            response = Response.status(Response.Status.OK).entity(jsonb.toJson(object)).build();
            return response;
        }
        else {
            String errorMessage = "Get Action Failed!";
            response = Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(errorMessage)).build();
            return  response;
        }
    }

    @POST
    @Path("/UpdateEntity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateEntity(Form form) {
        Response response = null;
        MultivaluedMap<String, String> map = form.asMap();
        boolean flag = true;

        String className = "com.team404.bookstore.entity." +
                map.get("ClassName").toString().replaceAll("[\\[\\]]","");

        String json = map.get("Json").toString().replaceAll("[\\[\\]]", "");

        try {
            Class<?> clz = Class.forName(className);
            Object entity = jsonb.fromJson(json, clz);
            flag = newUnifiedDao.UpdateEntity(entity);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(flag);
        if(flag) {
            response = Response.status(Response.Status.OK).entity(jsonb.toJson(flag)).build();
            return response;
        }
        else {
            String errorMessage = "Update Action Failed!";
            response = Response.status(Response.Status.BAD_REQUEST).entity(jsonb.toJson(errorMessage)).build();
            return  response;
        }
    }
}
