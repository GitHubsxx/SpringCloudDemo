package com.scc.service.impl;

import com.scc.dao.OrderDao;
import com.scc.domain.Order;
import com.scc.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService{

    @Autowired
    private OrderDao orderDao;

    @Override
    public void save(Integer uid,Integer vid) {
        orderDao.save(uid,vid);
    }

    @Override
    public List<Order> findAll(int page, int limit) {
        return orderDao.findAll(page, limit);
    }

    @Override
    public List<Order> findByName(int page, int limit, String username) {
        return orderDao.findByName(page,limit,username);
    }

    @Override
    public int total() {
        return orderDao.total();
    }

    @Override
    public void delete(Integer id) {
        orderDao.delete(id);
    }

    @Override
    public int count(String username) {
        return orderDao.count(username);
    }
}
