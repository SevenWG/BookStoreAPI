package com.team404.bookstore.dao;

import com.team404.bookstore.entity.OrderBookEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.util.List;

public class OrderBookDao {
    private static SessionFactory sessionFactory = HibernateConnection.singleSessionFactiory();

    public void AddOrderBook(OrderBookEntity orderBookEntity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(orderBookEntity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<OrderBookEntity> GetOrderBookByOid(int orderid) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<OrderBookEntity> list = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.getNamedQuery("GetOrderBookByOidQuery");
            query.setParameter("orderid", orderid);
            list = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }
}
