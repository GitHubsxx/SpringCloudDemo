package com.scc.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 生产者
 */
@Component
public class Send {
    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void send(){
        String content = "hello" + new Date();
        System.out.println("Sender:" +content);
        this.rabbitmqTemplate.convertAndSend("hello", content);
        System.out.println("============send 成功");
    }

}
