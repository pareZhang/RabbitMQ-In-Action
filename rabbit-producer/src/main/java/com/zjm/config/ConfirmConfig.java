package com.zjm.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author pareZhang
 * @Date 2020/5/23 12:18
 **/
@Configuration
public class ConfirmConfig {
    /**
     * 创建确认交换机
     */
    @Bean
    public Exchange confirmExchange(){
        return new TopicExchange("confirmExchange",true,false);
    }
    /**
     * 创建消息确认队列
     */
    @Bean
    Queue confirmQueue(){
        return new Queue("confirm_queue",true);
    }
    @Bean
    Binding bind(){
        return BindingBuilder.bind(confirmQueue()).to(confirmExchange()).with("boot.#").noargs();
    }
}
