package com.test.client.util;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

public final class Util {

  private Util() {
    throw new UnsupportedOperationException();
  }

  public static HttpEntity<String> createDocumentEntity(String key, String data) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    JSONObject body = new JSONObject();
    body.put("key", key);
    body.put("data", data);
    return new HttpEntity<>(body.toString(), headers);
  }
}
