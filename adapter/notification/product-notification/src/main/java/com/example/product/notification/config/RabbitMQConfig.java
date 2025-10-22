package com.example.product.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  public static final String EXCHANGE_NAME = "events";
  public static final String PRODUCT_CREATE_ROUTING_KEY = "product.v1.created";
  public static final String PRODUCT_UPDATE_ROUTING_KEY = "product.v1.updated";
  public static final String PRODUCT_CREATE_NOTIFICATION_QUEUE_NAME = "product.create.notification.v1";
  public static final String PRODUCT_UPDATE_NOTIFICATION_QUEUE_NAME = "product.update.notification.v1";

  @Bean
  public TopicExchange productExchange() {
    return new TopicExchange(EXCHANGE_NAME, true, false);
  }

  @Bean
  public Queue productCreateNotificationV1() {
    return QueueBuilder.durable(PRODUCT_CREATE_NOTIFICATION_QUEUE_NAME).quorum().build();
  }

  @Bean
  public Queue productUpdateNotificationV1() {
    return QueueBuilder.durable(PRODUCT_UPDATE_NOTIFICATION_QUEUE_NAME).quorum().build();
  }

  @Bean
  public Binding productCreateNotificationBinding(
      Queue productCreateNotificationV1, TopicExchange productExchange) {
    return BindingBuilder.bind(productCreateNotificationV1).to(productExchange).with(PRODUCT_CREATE_ROUTING_KEY);
  }

  @Bean
  public Binding productUpdateNotificationBinding(
      Queue productUpdateNotificationV1, TopicExchange productExchange) {
    return BindingBuilder.bind(productUpdateNotificationV1).to(productExchange).with(PRODUCT_UPDATE_ROUTING_KEY);
  }

  @Bean
  public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(
      ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(new Jackson2JsonMessageConverter());
    return factory;
  }

}
