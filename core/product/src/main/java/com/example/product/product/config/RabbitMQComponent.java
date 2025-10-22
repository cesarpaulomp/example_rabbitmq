package com.example.product.product.config;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.example.product.product.controller.dto.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RabbitMQComponent {
  final RabbitTemplate rabbitTemplate;
  final ObjectMapper objectMapper = new ObjectMapper();

  public RabbitMQComponent(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendProductCreatedMessage(Product product) throws JsonProcessingException {
    this.sendMessage(RabbitMQConfig.EXCHANGE_NAME,
        RabbitMQConfig.PRODUCT_CREATE_ROUTING_KEY,
        product);
  }

  public void sendProductUpdateMessage(Product product) throws JsonProcessingException {
    this.sendMessage(RabbitMQConfig.EXCHANGE_NAME,
        RabbitMQConfig.PRODUCT_UPDATE_ROUTING_KEY,
        product);
  }

  public void sendMessage(String exchange, String routingKey, Object payload) throws JsonProcessingException {
    String jsonPayload = objectMapper.writeValueAsString(payload);
    MessagePostProcessor withMetadata = message -> {
      MessageProperties props = message.getMessageProperties();
      props.setMessageId(UUID.randomUUID().toString());
      props.setTimestamp(new Date());
      props.setAppId("product-service");
      props.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
      return message;
    };
    rabbitTemplate.convertAndSend(exchange, routingKey, jsonPayload, withMetadata);
  }
}
