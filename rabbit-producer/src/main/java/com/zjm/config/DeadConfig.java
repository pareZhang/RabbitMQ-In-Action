package com.zjm.config;

import lombok.extern.java.Log;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author pareZhang
 * @Date 2020/5/20 12:26
 **/
@Configuration
@Log
public class DeadConfig {
    /**
     * 业务交换机
     * @return
     */
    @Bean
    DirectExchange testDeadExchange(){
        return new DirectExchange("TestDeadExchange");
    }

    /**
     * 业务队列
     * @return
     */
    @Bean
    public Queue testDeadQueue(){
        Map<String,Object> agrs=new HashMap<>(2);
        agrs.put("x-dead-letter-exchange","DeadExchange");
        agrs.put("x-dead-letter-routing-key","DeadRouting");
        return new Queue("TestDeadQueue",true,false,false,agrs);
    }
    @Bean
    Binding bindingTestDeadQueue(){
        return BindingBuilder.bind(testDeadQueue()).to(testDeadExchange()).with("TestDeadRouting");
    }

    /**
     * 死信交换机
     * @return
     */
    @Bean
    DirectExchange deadExchange(){
        return new DirectExchange("DeadExchange");
    }

    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue deadQueue(){
        return new Queue("DeadQueue",true);
    }
    @Bean
    Binding bindingDeadQueue(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("DeadRouting");
    }

}

