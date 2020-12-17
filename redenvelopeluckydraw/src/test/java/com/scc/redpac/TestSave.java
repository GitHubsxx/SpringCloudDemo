package com.scc.redpac;

import com.scc.redpac.domain.RedPacketInfo;
import com.scc.redpac.service.impl.RedPacketService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)//自动创建Spring的应用上下文
public class TestSave {
    @Autowired
    private RedPacketService redPacketService;
    @org.junit.jupiter.api.Test
    void saveRedPacket() {
        RedPacketInfo info = new RedPacketInfo();
        info.setUid(1111);
        info.setTotalPacket(5);
        info.setTotalAmount(20);
        redPacketService.saveRedPacket(info);
    }
}
