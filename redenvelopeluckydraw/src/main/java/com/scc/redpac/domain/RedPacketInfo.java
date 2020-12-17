package com.scc.redpac.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 红包类
 * @auth sxx
 * @date 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("red_packet_info")
public class RedPacketInfo extends Model<RedPacketInfo> {
    private Long id;
    private Long redPacketId;//红包id，采用timestamp+5位随机数
    private Integer totalAmount;//红包总金额，单位分
    private Integer totalPacket;//红包总个数
    private Integer remainingAmount;//剩余红包金额，单位分
    private Integer remainingPacket;//剩余红包个数
    private Integer uid;//新建红包用户的用户标识
    private Date createTime;
    private Date updateTime;
}
