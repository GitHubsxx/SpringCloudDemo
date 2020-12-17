package com.scc.domain;

import lombok.Data;

import java.util.Date;

/**
 * 红包类
 * @auth sxx
 * @date 2020-12-09
 */
@Data
public class RedPacketInfo {
    private Integer id;
    private Long redPacketId;//红包id，采用timestamp+5位随机数
    private Integer totalAmount;//红包总金额，单位分
    private Integer totalPacket;//红包总个数
    private Integer remainingAmount;//剩余红包金额，单位分
    private Integer remainingPacket;//剩余红包个数
    private Integer uid;//新建红包用户的用户标识
    private Date createTime;
    private Date updateTime;
}
