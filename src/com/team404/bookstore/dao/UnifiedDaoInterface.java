package com.team404.bookstore.dao;

public interface UnifiedDaoInterface<T> {

    public int AddEntity(T entity);
    public boolean DeleteEntity(T entity);
    public T GetEntityById(Class<T> clz, int id);
    public boolean UpdateEntity(T entity);
}
