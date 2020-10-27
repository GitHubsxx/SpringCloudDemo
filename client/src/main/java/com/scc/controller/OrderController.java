package com.scc.controller;

import com.scc.domain.OrderVo;
import com.scc.domain.User;
import com.scc.fegin.OrderFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderFegin orderFegin;

    @GetMapping("/save/{vid}")
    public String save(@PathVariable("vid") Integer vid,HttpSession session){
        User user=(User)session.getAttribute("user");
        orderFegin.save(user.getId(),vid);
        return "my_order";
    }

    @GetMapping("/findAll")
    @ResponseBody
    public OrderVo findAll(@RequestParam("page") int page , @RequestParam("limit") int limit){
        return orderFegin.findAll(page, limit);
    }

    @GetMapping("/findByName")
    @ResponseBody
    public OrderVo findByName(@RequestParam("page") int page , @RequestParam("limit") int limit,HttpSession session){
        User user=(User)session.getAttribute("user");
        return orderFegin.findByName(page,limit,user.getUsername());
    }

    //前台订单删除
    @GetMapping("/delete1/{id}")
    public String delete1(@PathVariable("id") Integer id){
        orderFegin.delete(id);
        return "my_order";
    }

    //后台订单删除
    @GetMapping("/delete2/{id}")
    public String delete2(@PathVariable("id") Integer id){
        orderFegin.delete(id);
        return "order_manage";
    }
}
