package com.example.product.notification.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.product.notification.config.RabbitMQConfig;

@Component
public class ProductCreateConsumer {
  @RabbitListener(queues = RabbitMQConfig.PRODUCT_CREATE_NOTIFICATION_QUEUE_NAME)
  public void onProductCreated(@Payload String payload) {
    System.out.println(payload);
  }
}
