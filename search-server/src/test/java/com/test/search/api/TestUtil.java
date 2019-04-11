package com.test.search.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

public final class TestUtil {

  private TestUtil() {
    throw new UnsupportedOperationException();
  }

  public static HttpEntity<String> createEntity(String body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    return new HttpEntity<>(body, headers);
  }
}
