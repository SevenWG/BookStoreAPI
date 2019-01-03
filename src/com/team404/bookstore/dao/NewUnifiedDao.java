package com.team404.bookstore.dao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.Method;

/*通用DAO类的另外一种实现方式
* 使用泛型，从而避免了使用Object而必须使用的强制转换
* 通过对类型Object的引用来实现参数的“任意化”，“任意化”带来的缺点是要做显式的强制类型转换，
* 而这种转换是要求开发者对实际参数类型可以预知的情况下进行的。
* 对于强制类型转换错误的情况，编译器可能不提示错误，在运行的时候才出现异常，这是一个安全隐患。
* 泛型的好处是在编译的时候检查类型安全，并且所有的强制转换都是自动和隐式的，提高代码的重用率
* 参考：https://www.cnblogs.com/fanjingfeng/p/6713722.html
* */
public class NewUnifiedDao<T> implements UnifiedDaoInterface<T> {

    public int AddEntity(T entity) {
        Session session = HibernateConnection.getSession();
        Transaction transaction = null;
        int id = 0;

        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            try{
                Method m = entity.getClass().getDeclaredMethod("getId");
                id = (int) m.invoke(entity);
            }catch (Exception e) {
                e.printStackTrace();
            }
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }

    public boolean DeleteEntity(T entity) {
        Session session = HibernateConnection.getSession();
        Transaction transaction = null;
        boolean flag = true;

        try {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            flag = false;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }

    public T GetEntityById(Class<T> clz,  int id) {
        Session session = HibernateConnection.getSession();
        Transaction transaction = null;
        T entity = null;
        try {
            transaction = session.beginTransaction();
            entity = session.get(clz, id);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  entity;
    }

    public boolean UpdateEntity(T entity) {
        Session session = HibernateConnection.getSession();
        Transaction transaction = null;
        boolean flag = true;

        try {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            flag = false;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return flag;
    }
}
