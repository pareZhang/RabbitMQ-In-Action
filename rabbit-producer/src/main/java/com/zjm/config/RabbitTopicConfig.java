package com.zjm.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author pareZhang
 * @Date 2020/5/18 11:34
 **/
@Configuration
public class RabbitTopicConfig {
    public final static String ITEM_TOPIC_EXCHANGE="item_topic_exchange";
    public final static String ITEM_QUEUE_1="item_queue_1";
    public final static String ITEM_QUEUE_2="item_queue_2";
    /**
     * 声明交换机
     */
    @Bean
    public Exchange topicExchange(){
        return new TopicExchange(ITEM_TOPIC_EXCHANGE,true,false);
    }
    /**
     * 声明队列
     */
    @Bean
    public Queue topicQueue1(){
       // return QueueBuilder.durable(ITEM_QUEUE).build(); 或者如下写法
        //此构造器默认开启持久化，不独占连接，过期不自动删除，还有其它构造器，可查看源码
        return new Queue(ITEM_QUEUE_1);
    }
    /**
     * 声明队列
     */
    @Bean
    public Queue topicQueue2(){
        // return QueueBuilder.durable(ITEM_QUEUE).build(); 或者如下写法
        //此构造器默认开启持久化，不独占连接，过期不自动删除，还有其它构造器，可查看源码
        return new Queue(ITEM_QUEUE_2);
    }
    /**
     * 队列绑定指定交换机
     */
    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("item.#").noargs();
    }
    /**
     * 队列绑定指定交换机
     */
    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("item.fuck.#").noargs();
    }

}
