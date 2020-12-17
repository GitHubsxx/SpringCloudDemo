package com.scc.redpac;

import java.util.Random;

public class Test {
    private static int onlyId = 0;
    //@org.junit.Test
    @org.junit.jupiter.api.Test
   public void test() throws InterruptedException {
        for (int i = 0; i < 10; i ++) {
            new Thread(new Runner(),"JUNIT多线程测试").start();
        }
        //想要正常输出的话可以让主线程不要结束，等待子线程全部运行结束后在结束主线程，输出结果就会正常
        Thread.sleep(10000);
    }
    class Runner implements Runnable {
        @Override
        public void run() {
            //ThreadLocalRandom current = ThreadLocalRandom.current();
            //onlyId = current.nextInt();
            int onlyId = new Random().nextInt();
            //Long onlyId = new SnowflakeIdWorker(0, 0).nextId();
            System.out.println("【当前线程ID】:"+Thread.currentThread().getId()+"-----id="+onlyId);
        }
    }
}
