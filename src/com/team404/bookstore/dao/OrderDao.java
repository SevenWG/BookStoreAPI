package com.team404.bookstore.dao;

import com.team404.bookstore.entity.OrdersEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.util.List;

public class OrderDao implements DaoFactory{

    /*获取自增id的配置！！！
    * 在实体类的xml文件中的id的配置行中添加<generator class="identity" />
    * */

    public int AddOrder (OrdersEntity orderEntity) {
        Session session = HibernateConnection.getSession();
        int id = 0;
        Transaction transaction = null;

        try {

            transaction = session.beginTransaction();
            int id1 = (Integer) session.save(orderEntity);
            id = orderEntity.getId();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return id;
    }


    public List<OrdersEntity> getListById (int userid) {
        Session session = HibernateConnection.getSession();
        Transaction transaction = null;
        List<OrdersEntity> list = null;

        try {
            transaction = session.beginTransaction();
            Query query = session.getNamedQuery("GetOdersByUidQuery");
            query.setParameter("userid", userid);
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

    public void UpdateOrderStatus(int id, boolean flag) {
        Session session = HibernateConnection.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query query = session.getNamedQuery("UpdateOrderStatusQuery");
            if(flag) {
                query.setParameter("status", "Success");
            } else {
                query.setParameter("status", "Failed");
            }
            query.setParameter("id", id);
            int result = query.executeUpdate();
            System.out.println("Rows affected: " + result + " Oid:" +id);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public OrdersEntity getEntityById (int id) {

        Session session = HibernateConnection.getSession();
        Transaction transaction = null;
        OrdersEntity orderEntity = null;

        try {
            transaction = session.beginTransaction();
            orderEntity = (OrdersEntity)session.get(OrdersEntity.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return orderEntity;
    }


}
