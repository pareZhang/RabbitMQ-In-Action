package com.zjm.controller;

import com.zjm.config.RabbitTopicConfig;
import com.zjm.service.QueueService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author pareZhang
 * @Date 2020/5/18 12:21
 **/
@RestController
public class SendMsgController {
    /**
     *注入RabbitMQ的模板
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private QueueService queueService;

    /**
     * 测试
     * @return
     */
    @GetMapping("/sendMsg")
    public String sendMsg(@RequestParam String msg, @RequestParam String routingKey){
        /**
         * 发送消息
         * 参数1：交换机的名称
         * 参数2：路由key
         * 参数3：发送的消息
         */
        rabbitTemplate.convertAndSend(RabbitTopicConfig.ITEM_TOPIC_EXCHANGE,routingKey,msg);
        //返回消息和路由键
        return "消息发送成功！"+"\n"+"发送的消息:"+msg+"\n"+"路由键："+routingKey;
    }
    @GetMapping("/ttlMsg")
    public void ttlMessgae(@RequestParam String routingKey){
       queueService.ttlMessage(routingKey);
    }


}
