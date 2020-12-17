package com.scc.redpac;

import com.scc.redpac.utils.SnowflakeIdWorker;

import java.util.Random;

public class Test3 {
    public static void main(String[] args) {
        Integer maxMoney = (int) (3 / (1 + 1) * 2);
        System.out.println(maxMoney+"========");
        Random ra = new Random();
        System.out.println(ra.nextInt(maxMoney));
    }
}
