package com.nixmash.rabbitmq.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by daveburke on 4/20/17.
 */
@Configuration
@EnableRabbit
@ComponentScan(basePackages = "com.nixmash.rabbitmq")
@PropertySource("classpath:application.properties")
public class ApplicationConfig {
}
