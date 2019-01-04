package com.team404.bookstore.dao;

import org.hibernate.SessionFactory;
import com.team404.bookstore.entity.UserEntity;
import org.hibernate.*;
import org.hibernate.Query;

import java.util.List;
import java.util.Map;

public class UserDao {
    private static SessionFactory sessionFactory = HibernateConnection.singleSessionFactiory();

    public int AddUser(UserEntity userEntity) {

        Session session = HibernateConnection.getSession();

        Transaction transaction = null;
        int id = 0;

        try {
            transaction = session.beginTransaction();
            session.save(userEntity);
            transaction.commit();
            id = userEntity.getId();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public UserEntity GetUserById (int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        UserEntity userEntity = null;
        try {
            transaction = session.beginTransaction();
            userEntity = (UserEntity) session.get(UserEntity.class, id);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  userEntity;
    }

    public UserEntity GetUserbyAccount (String username) {

        Session session = sessionFactory.openSession();

        Transaction transaction = null;
        UserEntity userEntity = null;

        try {
            transaction = session.beginTransaction();
            Query query = session.getNamedQuery("GetUserbyAccountQuery");
            query.setParameter("username", username);
            List<UserEntity> list = query.list();
            transaction.commit();
            if(list.size() != 0) {
                userEntity = (UserEntity)list.get(0);
            }

        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userEntity;
    }

}
