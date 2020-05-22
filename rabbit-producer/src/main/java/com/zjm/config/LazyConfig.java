package com.zjm.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author pareZhang
 * @Date 2020/5/22 18:43
 **/
@Configuration
public class LazyConfig {
    /**
     * 声明一个业务交换机
     */
    @Bean
    Exchange directExchange(){
        return new DirectExchange("ServiceExchange");
    }
    /**
     * 声明第一个业务缓冲队列，设置10秒过期
     */
    @Bean
    Queue directQueue1(){
        Map<String,Object> args=new HashMap<>(3);
        //对队列里的消息10秒过期
        args.put("x-message-ttl", 1000L);
        //消息过期由DLXExchange重发消息
        args.put("x-dead-letter-exchange","DLXExchange");
        //重发消息携带的路由键
        args.put("x-dead-letter-routing-key","ttl.10s");
        return new Queue("directQueue1",true,false,false,args);
    }
    /**
     * 声明第二个业务缓冲队列，设置20秒过期
     */
    @Bean
    Queue directQueue2(){
        Map<String,Object> args=new HashMap<>(3);
        //对队列里的消息20秒过期
        //args.put("x-message-ttl","2000");注意这里参数类型为number不是String！！！！mmp
        args.put("x-message-ttl", 20000L);
        args.put("x-dead-letter-exchange","DLXExchange");
        args.put("x-dead-letter-routing-key","ttl.20s");
        return new Queue("directQueue2",true,false,false,args);
    }
    @Bean
    Binding directQueueBind1(){
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("ttl").noargs();
    }
    @Bean
    Binding directQueueBind2(){
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with("ttl").noargs();
    }

    /**
     * 声明一个死信交换机DLXExchange 用来republish过期消息到死信队列
     */
    @Bean
    public Exchange dlxExchange(){
        return new TopicExchange("DLXExchange");
    }
    /**
     * 声明一个延迟（死信）队列来接收过期消息  达到延迟效果
     */
    @Bean
    Queue dlxQueue(){
        return new Queue("delayQueue");
    }
    /**
     * （延迟）死信队列绑定死信交换机
     */
    @Bean
    Binding delayBind(){
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with("ttl.#").noargs();
    }
}
