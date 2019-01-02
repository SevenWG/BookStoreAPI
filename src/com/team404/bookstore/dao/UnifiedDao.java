package com.team404.bookstore.dao;

import com.team404.bookstore.entity.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UnifiedDao {

    public Object GetEntityById(String className, int id) {
        String fullClassName = "com.team404.bookstore.entity." + className;
        Object object = null;

        Session session = HibernateConnection.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            try {
                Class<?> clz = Class.forName(fullClassName);
                transaction = session.beginTransaction();
                object = session.get(clz, id);
            }catch (Exception e) {
                e.printStackTrace();
            }

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  object;
    }
}
