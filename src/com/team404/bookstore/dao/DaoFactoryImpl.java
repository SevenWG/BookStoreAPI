package com.team404.bookstore.dao;

import java.util.List;
import java.lang.reflect.Method;

/*
 * Implementation of Factory Pattern & Singleton Pattern
 * */

public class DaoFactoryImpl{

    private static DaoFactoryImpl daoFactory = null;

    public static DaoFactoryImpl SingleDaoFactory() {
        if(daoFactory == null) {
            synchronized (DaoFactoryImpl.class) {
                if(daoFactory == null) {
                    daoFactory = new DaoFactoryImpl();
                }
            }
        }
        return  daoFactory;
    }

    public List<?> ListSomethingById(String classname, String methodName, int id) {
        List<?> list= null;

        try {
            String className = "com.team404.bookstore.dao."+classname;

            Class<?> clz = Class.forName(className);

            Object obj = (DaoFactory)clz.getDeclaredConstructor().newInstance();

            Method m = obj.getClass().getDeclaredMethod(methodName, int.class);

            list = (List<?>) m.invoke(obj, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public Object getEntityById(String classname, String methodName, int id) {
        Object entity = null;

        try {
            String className = "com.team404.bookstore.dao."+classname;

            Class clz = Class.forName(className);

            Object obj = clz.newInstance();

            Method m = obj.getClass().getDeclaredMethod(methodName, int.class);

            entity = m.invoke(obj, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

}
