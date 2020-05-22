package com.zjm;

import com.zjm.service.QueueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitProducerApplicationTests {

	@Autowired
	private QueueService queueService;
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

}
