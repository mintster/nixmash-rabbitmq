package com.nixmash.rabbitmq;

import com.nixmash.rabbitmq.components.RabbitUI;
import com.nixmash.rabbitmq.config.ApplicationConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ApplicationConfig.class);
		ctx.refresh();
		RabbitUI ui = ctx.getBean(RabbitUI.class);
		ui.init();
		ctx.close();
	}
}
