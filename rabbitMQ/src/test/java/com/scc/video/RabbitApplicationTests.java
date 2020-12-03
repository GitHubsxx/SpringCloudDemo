package com.scc.video;

import com.scc.RabbitApplication;
import com.scc.rabbitMQ.Send;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes={RabbitApplication.class})
public class RabbitApplicationTests {
	@Autowired
	private Send sender;

	@Test
	public void hello(){
		sender.send();
	}

}
