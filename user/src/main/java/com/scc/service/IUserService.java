package com.scc.service;

import com.scc.domain.User;

public interface IUserService {
    //登录
    public User login(String username, String password);

    //注册
    public void register(User user);

    //更新用户信息
    public void update(User user);
}
