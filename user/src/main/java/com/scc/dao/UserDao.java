package com.scc.dao;

import com.scc.domain.User;

public interface UserDao {
    //登录
    public User login(String username, String password);

    //注册
    public void register(User user);

    //更新用户信息
    public void update(User user);

}
