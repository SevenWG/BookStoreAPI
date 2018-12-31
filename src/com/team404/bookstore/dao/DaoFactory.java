package com.team404.bookstore.dao;

import java.util.List;

public interface DaoFactory<T> {

    public List<T> getListById(int id);

    public T getEntityById(int id);

}