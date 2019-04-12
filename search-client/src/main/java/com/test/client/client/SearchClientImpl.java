package com.test.client.client;

import com.test.client.client.exception.NotFoundException;
import com.test.client.client.model.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static com.test.client.util.Util.createDocumentEntity;

@Component
public class SearchClientImpl implements SearchClient {

  @Value("${search.server.url}")
  private String serverUrl;
  private final RestTemplate restTemplate;

  @Autowired
  public SearchClientImpl(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

  public Set<String> search(String tokens) throws NotFoundException {
    ResponseEntity<Set<String>> response =
        restTemplate.exchange(serverUrl + "/search?q=" + tokens, HttpMethod.GET, null,
            new ParameterizedTypeReference<Set<String>>() {});
    return response.getBody();
  }

  public void put(String key, String data) throws NotFoundException {
    HttpEntity<String> entity = createDocumentEntity(key, data);
    restTemplate.postForEntity(serverUrl + "/documents", entity, String.class);
  }

  public Document get(String key) throws NotFoundException {
    ResponseEntity<Document> responseEntity = restTemplate.getForEntity(serverUrl + "/documents/" + key, Document.class);
    return responseEntity.getBody();
  }
}
