package com.scc.controller;

import com.scc.domain.User;
import com.scc.fegin.UserFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserFegin userFegin;

    //登录
    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,HttpSession session){
       User user=userFegin.login(username,password);
       String target=null;
       if(user!=null){
           session.setAttribute("user", user);
           if(user.getRole().equals("admin")){
               target="main";
           }else{
               target="index";
           }
       }else {
               target="login";
       }
       return target;
    }

    //注册
    @PostMapping("/register")
    public String register(User user){
        userFegin.register(user);
        return "index";
    }

    //更新用户信息
    @PostMapping("/update")
    public String update(User user,HttpSession session){
        userFegin.update(user);
        User user1=(User)session.getAttribute("user");
        if(!user1.getPassword().equals(user.getPassword())){
            session.invalidate();
            return "login";
        }else {
            user.setId(user1.getId());
            session.setAttribute("user", user);
            return "index";
        }
    }

    //退出
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @RequestMapping("/redirect/{target}")
    public String redirect(@PathVariable("target") String target){
        return target;
    }


}
