package com.scc.fegin;

import com.scc.domain.RedPacketInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("redPacket")
public interface RedPacketFegin {
    /***
     * 发红包
     * @param "uid ": 发红包的用户id
     * @param "totalAmount" : 红包金额
     * @param "totalPacket" : 红包总个数
     * @return
     */
    @PutMapping("/addPacket")
    public String saveRedPacket(@RequestBody RedPacketInfo redPacketInfo);
    /**
     * 抢红包
     * @param redPacketId 红包id
     * @param uid 用户id
     * @return
     */
    @GetMapping("/getPacket/{redPacketId}/{uid}")
    public String getRedPacket(@PathVariable("redPacketId")Long redPacketId, @PathVariable("uid") Integer uid);
    /**
     * 拆红包
     * @param redPacketId 红包id
     * @param uid 用户id
     * @return
     */
    @GetMapping("/getRedPacketMoney/{uid}/{redPacketId}")
    public String getRedPacketMoney(@PathVariable("uid") int uid,@PathVariable("redPacketId") long redPacketId);
}
