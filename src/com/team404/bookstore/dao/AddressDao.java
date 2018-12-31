package com.team404.bookstore.dao;

import com.team404.bookstore.entity.AddressEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.util.List;

public class AddressDao {
    private static SessionFactory sessionFactory = HibernateConnection.singleSessionFactiory();

    public int AddAddress(AddressEntity addressEntity) {

        Session session = HibernateConnection.getSession();
        Transaction transaction = null;
        int id = 0;

        try {
            transaction = session.beginTransaction();
            id = (Integer)session.save(addressEntity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return id;

    }

    public AddressEntity getAddressByUid(int userid) {

        Session session = HibernateConnection.getSession();

        Transaction transaction = null;
        AddressEntity addressEntity = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.getNamedQuery("getAddressByUidQuery");
            query.setParameter("userid", userid);
            List<AddressEntity> list = query.list();
            transaction.commit();
            if(list.size() != 0) {
                addressEntity = (AddressEntity) list.get(0);
            }
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  addressEntity;
    }
}