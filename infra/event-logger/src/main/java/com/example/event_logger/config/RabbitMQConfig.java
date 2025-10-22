package com.example.event_logger.config;

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
  public static final String EVENT_LOGGER_QUEUE_NAME = "events.event.logger.v2";

  @Bean
  public TopicExchange productExchange() {
    return new TopicExchange(EXCHANGE_NAME, true, false);
  }

  @Bean
  public Queue eventLoggerQueue() {
    return QueueBuilder.durable(EVENT_LOGGER_QUEUE_NAME).quorum().build();
  }

  @Bean
  public Binding eventLoggerBinding(Queue eventLoggerQueue, TopicExchange eventExchange) {
    return BindingBuilder.bind(eventLoggerQueue).to(eventExchange).with("#");
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
