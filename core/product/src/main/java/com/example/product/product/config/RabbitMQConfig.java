package com.example.product.product.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  public static final String EXCHANGE_NAME = "events";
  public static final String PRODUCT_CREATE_ROUTING_KEY = "product.v1.created";
  public static final String PRODUCT_UPDATE_ROUTING_KEY = "product.v1.updated";

  @Bean
  public TopicExchange productExchange() {
    return new TopicExchange(EXCHANGE_NAME, true, false);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(new Jackson2JsonMessageConverter());
    return template;
  }
}
