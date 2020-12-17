package com.scc.redpac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scc.redpac.domain.RedPacketInfo;

public interface IRedPacketService extends IService<RedPacketInfo> {
    //发红包
    public String saveRedPacket(RedPacketInfo redPacketInfo);
    //抢红包
    public String getRedPacket(Long redPacketId, Integer uid);
    //拆红包
    public String getRedPacketMoney(Integer uid, Long redPacketId);
}
