package com.example.product.product.service;

import org.springframework.stereotype.Service;

import com.example.product.product.config.RabbitMQComponent;
import com.example.product.product.controller.dto.Product;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class ProductService {

  private final RabbitMQComponent rabbitMQComponent;

  public ProductService(RabbitMQComponent rabbitMQComponent) {
    this.rabbitMQComponent = rabbitMQComponent;
  }

  public void createProduct(Product product) throws JsonProcessingException {
    rabbitMQComponent.sendProductCreatedMessage(product);
  }

  public void updateProduct(Product product) throws JsonProcessingException {
    rabbitMQComponent.sendProductUpdateMessage(product);
  }
}
