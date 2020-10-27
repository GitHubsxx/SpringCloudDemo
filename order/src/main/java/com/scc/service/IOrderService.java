package com.scc.service;

import com.scc.domain.Order;

import java.util.List;

public interface IOrderService {

    //保存订单
    public void save(Integer uid,Integer vid);

    //查询所有订单
    public List<Order> findAll(int page, int limit);

    //根据用户名查询订单
    public List<Order> findByName(int page, int limit,String username);

    //查询所有记录条数
    public int total();

    //删除订单
    public void delete(Integer id);

    //查询该用户的订单记录条数
    public int count(String username);
}
