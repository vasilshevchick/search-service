package com.test.client.handler;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse httpResponse) {
    return false;
  }

  @Override
  public void handleError(ClientHttpResponse clientHttpResponse) {
    //ignore
  }
}