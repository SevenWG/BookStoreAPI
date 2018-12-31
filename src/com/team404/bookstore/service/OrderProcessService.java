package com.team404.bookstore.service;

import com.team404.bookstore.dao.UserDao;
import com.team404.bookstore.entity.UserEntity;

public class OrderProcessService {
    private  UserDao userDao;

    public UserEntity GetUserByAccount(String username) {
        userDao = new UserDao();

        UserEntity userEntity = userDao.GetUserbyAccount(username);

        return  userEntity;
    }

}
