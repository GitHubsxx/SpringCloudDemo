package com.scc.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    private Integer id;
    private Date date;
    private User user;
    private Video video;
}
