package com.example.product.product.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.product.controller.dto.Product;
import com.example.product.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public void createProduct(@RequestBody Product product) throws JsonProcessingException {
    productService.createProduct(product);
  }

  @PutMapping
  public void updateProduct(@RequestBody Product product) throws JsonProcessingException {
    productService.updateProduct(product);
  }
}
