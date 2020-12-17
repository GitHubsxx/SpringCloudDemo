package com.scc.redpac;

import com.scc.redpac.utils.SnowflakeIdWorker;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Test1 {
    private static final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
    private static final Random ra = new Random();
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i ++) {
            new Thread(new Runner(),"Main多线程测试").start();
        }
        //想要正常输出的话可以让主线程不要结束，等待子线程全部运行结束后在结束主线程，输出结果就会正常
        Thread.sleep(10000);
    }
   static class Runner implements Runnable {
        @Override
        public void run() {
            //ThreadLocalRandom current = ThreadLocalRandom.current();
            //int onlyId = current.nextInt();
            int onlyId = ra.nextInt();
           // Long onlyId = idWorker.nextId();
            System.out.println("【当前线程ID】:"+Thread.currentThread().getId()+"-----id="+onlyId);
        }
    }
}
