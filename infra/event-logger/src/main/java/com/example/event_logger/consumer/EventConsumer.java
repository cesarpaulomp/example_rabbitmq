package com.example.event_logger.consumer;

import java.time.Instant;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.example.event_logger.config.RabbitMQConfig;
import com.example.event_logger.entity.EventLog;

@Component
public class EventConsumer {
  @RabbitListener(queues = RabbitMQConfig.EVENT_LOGGER_QUEUE_NAME)
  public void onProductCreated(
      @Header("amqp_receivedRoutingKey") String routingKey,
      @Header("amqp_receivedExchange") String exchange,
      Message message) {
    String eventId = message.getMessageProperties().getMessageId();
    Instant eventDate = message.getMessageProperties().getTimestamp().toInstant();
    String app = message.getMessageProperties().getAppId();
    String body = new String(message.getBody());

    var event = new EventLog(eventId, app, eventDate, exchange, routingKey, body);
    System.out.println(event.toString());
  }
}
