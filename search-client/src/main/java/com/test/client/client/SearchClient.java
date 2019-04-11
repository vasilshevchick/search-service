package com.test.client.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.test.client.util.Util.createDocumentEntity;

@Component
public class SearchClient {

  @Value("${search.server.url}")
  private String serverUrl;
  private final RestTemplate restTemplate;

  @Autowired
  public SearchClient(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

  public String search(String tokens) {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(serverUrl + "/search?q=" + tokens, String.class);
    int statusCode = responseEntity.getStatusCodeValue();
    if (statusCode == 200) {
      return "Success: " + responseEntity.getBody();
    } else if (statusCode == 404) {
      return "Documents not found";
    } else {
      return "Error: " + responseEntity.getBody();
    }
  }

  public String put(String key, String data) {
    HttpEntity<String> entity = createDocumentEntity(key, data);
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(serverUrl + "/documents", entity, String.class);
    if (responseEntity.getStatusCodeValue() == 200) {
      return "Success:";
    } else {
      return "Error: " + responseEntity.getBody();
    }
  }

  public String get(String key) {
    ResponseEntity<String> responseEntity = restTemplate.getForEntity(serverUrl + "/documents/" + key, String.class);
    int statusCode = responseEntity.getStatusCodeValue();
    if (statusCode == 200) {
      return "Success: " + responseEntity.getBody();
    } else if (statusCode == 404) {
      return "Document not found";
    } else {
      return "Error: " + responseEntity.getBody();
    }
  }
}
