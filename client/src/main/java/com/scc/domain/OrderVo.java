package com.scc.domain;

import lombok.Data;

import java.util.List;

@Data
public class OrderVo {
    private int code;
    private String msg;
    private int count;
    private List<Order> data;
}
