package com.zjm.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @Author pareZhang
 * @Date 2020/5/23 17:23
 **/
@Component
public class TopicSend implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 如果消息没有到exchange,则confirm回调,ack=false
     * 如果消息到达exchange,则confirm回调,ack=true
     * exchange到queue成功,则不回调return
     * exchange到queue失败,则回调return(需设置mandatory=true,否则不会回调,消息就丢了)
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息id："+correlationData.getId());
        if (ack){
            System.out.println("消息发送确认成功！");
        }else {
            System.out.println("消息发送确认失败，原因："+cause);
        }

    }
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        try {
            System.out.println("return-message:"+new String(message.getBody(),"UTF-8")+",replycode:"+replyCode+
            ",replyText:"+replyText+",exchange:"+exchange+",routingKey:"+routingKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void send(String msg){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        CorrelationData correlationData=new CorrelationData(UUID.randomUUID().toString());
        System.out.println("发送的消息为："+msg);
        this.rabbitTemplate.convertAndSend("confirm_exchange","boot.she",msg,correlationData);
    }

}
