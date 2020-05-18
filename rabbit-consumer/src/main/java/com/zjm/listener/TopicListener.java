package com.zjm.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author pareZhang
 * @Date 2020/5/18 17:38
 * 消费者监听类
 **/
@Component
public class TopicListener {
    @RabbitListener(queues = "item_queue_1")
    public void handler1(String msg){
        System.out.println("消费者1消费了消息："+msg);
    }
    @RabbitListener(queues = "item_queue_2")
    public void handler2(String msg){
        System.out.println("消费者2消费了消息："+msg);
    }
}
