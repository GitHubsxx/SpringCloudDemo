package com.scc.controller;

import com.scc.domain.User;
import com.scc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    //登录
    @GetMapping("/login/{username}/{password}")
    public User login(@PathVariable("username") String username,@PathVariable("password") String password){
        User user=userService.login(username,password);
        return user;
    }

    //注册
    @PostMapping("/register")
    public void register(@RequestBody User user) {
        userService.register(user);
    }

    //更新用户信息
    @PutMapping("/update")
    public  void update(@RequestBody User user){
        userService.update(user);
    }
}
