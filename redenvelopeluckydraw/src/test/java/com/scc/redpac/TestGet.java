package com.scc.redpac;

import com.scc.redpac.domain.RedPacketInfo;
import com.scc.redpac.service.impl.RedPacketService;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)//自动创建Spring的应用上下文
class TestGet{
    @Autowired
    private RedPacketService redPacketService;
    //org.junit.Test  不支持多线程
    @org.junit.jupiter.api.Test
    void getRedPacket() throws InterruptedException{
        redPacketService.getRedPacket(390826236810625024L,2222);//拆红包
        /*//模拟10人拆红包
        for (int i = 0; i < 10; i ++) {
            new Thread(new Runner(),"JUNIT多线程测试").start();
        }
        Thread.sleep(10000);//想要正常输出的话可以让主线程不要结束，等待子线程全部运行结束后在结束主线程，输出结果就会正常*/
    }
    class Runner implements Runnable {
        @Override
        public void run() {
            redPacketService.getRedPacket(786517237199536128L,1111);//拆红包
            System.out.println("【当前线程ID】:"+Thread.currentThread().getId());
        }
    }
}
