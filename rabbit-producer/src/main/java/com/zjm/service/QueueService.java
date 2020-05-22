package com.zjm.service;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.BMPattern;
import lombok.extern.java.Log;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author pareZhang
 * @Date 2020/5/19 19:50
 **/
@Service
@Log
public class QueueService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 设置单条消息过期时间
     * @param routingKey
     */
    public void ttlMessage(String routingKey){
        MessageProperties messageProperties=new MessageProperties();
        //设置消息10秒过期
        messageProperties.setExpiration("10000");
        Message message=new Message("测试过期时间：10秒过期！".getBytes(),messageProperties);
        //发消息
        rabbitTemplate.convertAndSend("ttl.#",message);
        System.out.println("发送成功："+message);
    }

    /**
     * 发送   死信
     */
    public void deadQueue(){
        String msgID=String.valueOf(UUID.randomUUID());
        String sendTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>(4);
        map.put("msgId",msgID);
        map.put("masgData","test dead letter queue");
        map.put("sendTime",sendTime);
        rabbitTemplate.convertAndSend("TestDeadExchange","TestDeadRouting",map);
    }

    /**
     * 延迟队列
     */
    public void delayQueue(){
        String msgId=String.valueOf(UUID.randomUUID());
        String sendTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>(3);
        map.put("msgId",msgId);
        map.put("sendTime",sendTime);
        map.put("message","这是一条测试消息");
        rabbitTemplate.convertAndSend("ServiceExchange","ttl",map);
    }

}
