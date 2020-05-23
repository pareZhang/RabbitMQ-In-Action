package com.zjm;

import com.zjm.config.TopicSend;
import com.zjm.service.QueueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitProducerApplicationTests {

	@Autowired
	private QueueService queueService;
	@Autowired
	private TopicSend send;

	@Test
	public void contextLoads() {
	}
	@Test
	public void testDeadQueue(){
		queueService.deadQueue();
	}
	@Test
	public void testDelayQueue(){
		queueService.delayQueue();
	}

	/**
	 * 消息确认回调
	 */
	@Test
	public void testConfirm(){
		send.send("测试着玩！");
	}

}
