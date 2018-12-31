package com.team404.bookstore.dao;

import com.team404.bookstore.entity.CategoryEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CategoryDao {

    private static SessionFactory sessionFactory = HibernateConnection.singleSessionFactiory();

    public CategoryEntity getCategoryById(int id) {
        Session session = sessionFactory.openSession();

        CategoryEntity categoryEntity = null;
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            categoryEntity = (CategoryEntity) session.get(CategoryEntity.class, id);
            transaction.commit();
        } catch ( HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return categoryEntity;
    }

    public List<CategoryEntity> ListCategory() {
        Session session = sessionFactory.openSession();

        Transaction transaction = null;
        List<CategoryEntity> list = null;
        try {
            transaction = session.beginTransaction();
            list = session.getNamedQuery("ListCategoryQuery").list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }
}
