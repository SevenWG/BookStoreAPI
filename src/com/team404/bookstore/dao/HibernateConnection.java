package com.team404.bookstore.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnection {
    private static SessionFactory sessionFactory = null;

    private static SessionFactory setSessionFactory() {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            return sessionFactory;
        }
        catch (Throwable ex)
        {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory singleSessionFactiory() {
        if(sessionFactory == null) {
            synchronized (HibernateConnection.class) {
                if(sessionFactory == null) {
                    sessionFactory = setSessionFactory();
                }
            }
        }
        return sessionFactory;
    }

    public static Session getSession() {
        if(sessionFactory == null) {
            synchronized (HibernateConnection.class) {
                if(sessionFactory == null) {
                    sessionFactory = setSessionFactory();
                }
            }
        }
        return sessionFactory.openSession();
    }
}
