package com.scc.redpac.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 红包记录
 * @auth sxx
 * @date 2020-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("red_packet_record")
public class RedPacketRecord extends Model<RedPacketRecord> {
    private Long id;
    private Integer amount;// '抢到红包的金额',
    private String  nickName;// '抢到红包的用户的用户名',
    private String  imgUrl;// '抢到红包的用户的头像',
    private Integer uid;// '抢到红包用户的用户标识',
    private Long redPacketId;// '红包id，采用timestamp+5位随机数',
    private Date createTime;
    private Date updateTime;//'更新时间',
}
