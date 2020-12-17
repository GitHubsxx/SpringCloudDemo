package com.scc.redpac;

import com.scc.redpac.service.impl.RedPacketService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)//自动创建Spring的应用上下文
public class TestUpdate {
    @Autowired
    private RedPacketService redPacketService;

    @org.junit.jupiter.api.Test
    void getRedPacketMoney() throws InterruptedException{
        //redPacketService.getRedPacketMoney(2222,786613704681586688L);//拆红包
        //模拟10人抢红包
        for (int i = 0; i < 10; i ++) {
            new Thread(new Runner(),"JUNIT多线程测试抢红包").start();
        }
        //想要正常输出的话可以让主线程不要结束，等待子线程全部运行结束后在结束主线程，输出结果就会正常
        Thread.sleep(10000);
    }
    class Runner implements Runnable {
        @Override
        public void run() {
            redPacketService.getRedPacketMoney((int)Thread.currentThread().getId(),390889722752270336L);//拆红包
            System.out.println("【当前线程ID】:"+Thread.currentThread().getId());
        }
    }
}
