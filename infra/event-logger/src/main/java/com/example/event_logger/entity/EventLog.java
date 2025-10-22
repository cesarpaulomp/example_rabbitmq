package com.example.event_logger.entity;

import java.time.Instant;

public record EventLog(
    String id,
    String app,
    Instant eventDate,
    String topic,
    String route,
    String payload) {

}
