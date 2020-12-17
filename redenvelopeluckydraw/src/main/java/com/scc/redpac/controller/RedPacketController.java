package com.scc.redpac.controller;

import com.scc.redpac.domain.RedPacketInfo;
import com.scc.redpac.service.IRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/redPacket")
@RestController
public class RedPacketController {
    @Autowired
    private IRedPacketService redPacketService;

    /***
     * 发红包
     * @param "uid ": 发红包的用户id
     * @param "totalAmount" : 红包金额
     * @param "totalPacket" : 红包总个数
     * @return
     */
    @PutMapping("/addPacket")
    public String saveRedPacket(@RequestBody RedPacketInfo redPacketInfo) {

        return this.redPacketService.saveRedPacket(redPacketInfo);
    }
    /**
     * 抢红包
     * @param redPacketId 红包id
     * @param uid 用户id
     * @return
     */
    @GetMapping("/getPacket/{redPacketId}/{uid}")
    public String getRedPacket(@PathVariable("redPacketId")Long redPacketId,@PathVariable("uid") Integer uid) {
        return redPacketService.getRedPacket(redPacketId,uid);
    }
    /**
     * 拆红包
     * @param redPacketId 红包id
     * @param uid 用户id
     * @return
     */
    @GetMapping("/getRedPacketMoney/{uid}/{redPacketId}")
    public String getRedPacketMoney(@PathVariable("uid") int uid,@PathVariable("redPacketId") long redPacketId) {
        return this.redPacketService.getRedPacketMoney(uid,redPacketId);
    }
}
