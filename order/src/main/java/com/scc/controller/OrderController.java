package com.scc.controller;

import com.scc.domain.Order;
import com.scc.domain.OrderVo;
import com.scc.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @GetMapping("/save/{uid}/{vid}")
    public void save(@PathVariable("uid") Integer uid, @PathVariable("vid") Integer vid){
        orderService.save(uid, vid);
    }

    @GetMapping("/findAll")
    public OrderVo findAll(@RequestParam("page") int page, @RequestParam("limit") int limit){
        List<Order> orders = orderService.findAll(page,limit );
        OrderVo orderVo=new OrderVo();
        orderVo.setCode(0);
        orderVo.setMsg("");
        orderVo.setCount(orderService.total());
        orderVo.setData(orders);
        return orderVo;
    }

    @GetMapping("/findByName")
    public OrderVo findByName(@RequestParam("page") int page, @RequestParam("limit") int limit,@RequestParam("username") String username){
        List<Order> orders = orderService.findByName(page,limit,username);
        OrderVo orderVo=new OrderVo();
        orderVo.setCode(0);
        orderVo.setMsg("");
        orderVo.setCount(orderService.count(username));
        orderVo.setData(orders);
        return orderVo;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id){
        orderService.delete(id);
    }

}
