package com.scc.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建一个name为‘hello’的队列
 * @date 2020-11-19
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue helloQueue(){
        return new Queue("hello");
    }

}
