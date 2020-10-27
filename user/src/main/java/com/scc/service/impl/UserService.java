package com.scc.service.impl;

import com.scc.dao.UserDao;
import com.scc.domain.User;
import com.scc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserDao userDao;

    @Override
    public User login(String username, String password) {
        return userDao.login(username, password);
    }

    @Override
    public void register(User user) {
        userDao.register(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
