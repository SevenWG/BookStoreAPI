package com.team404.bookstore.dao;

import com.team404.bookstore.entity.CountsEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CountDao {

    public CountsEntity getCount() {

        Session session = HibernateConnection.getSession();

        CountsEntity countEntity = null;
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            countEntity = (CountsEntity) session.get(CountsEntity.class, 1);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return countEntity;
    }

    public void CountUpdate() {

        Session session = HibernateConnection.getSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            CountsEntity countEntity = getCount();

            int count = countEntity.getCounts();
            int newCount = count+1;

            countEntity.setCounts(newCount);
            session.update(countEntity);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}