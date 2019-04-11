package com.test.client.configuration;

import com.test.client.handler.RestTemplateResponseErrorHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  private final RestTemplateResponseErrorHandler handler;

  @Autowired
  public AppConfig(RestTemplateResponseErrorHandler handler) {this.handler = handler;}

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(handler);
    return restTemplate;
  }
}
