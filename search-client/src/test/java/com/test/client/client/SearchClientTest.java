package com.test.client.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.test.client.util.Util.createDocumentEntity;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchClientTest {

  @Mock
  private RestTemplate restTemplate;
  @InjectMocks
  private SearchClient searchClient;

  @Test
  public void shouldGetDocumentSuccessfully() {
    //GIVEN
    String key = "key";
    String data = "DocumentData";
    ResponseEntity<String> responseEntity = new ResponseEntity<>(data, HttpStatus.OK);
    when(restTemplate.getForEntity(endsWith(key), eq(String.class))).thenReturn(responseEntity);

    //WHEN
    String actual = searchClient.get(key);

    //THEN
    assertThat(actual, is("Success: DocumentData"));
  }

  @Test
  public void shouldGetNotFoundErrorOnGet() {
    //GIVEN
    String key = "key";
    ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    when(restTemplate.getForEntity(endsWith(key), eq(String.class))).thenReturn(responseEntity);

    //WHEN
    String actual = searchClient.get(key);

    //THEN
    assertThat(actual, is("Document not found"));
  }

  @Test
  public void shouldGetErrorOnGet() {
    //GIVEN
    String key = "key";
    ResponseEntity<String> responseEntity = new ResponseEntity<>("Some Message", HttpStatus.INTERNAL_SERVER_ERROR);
    when(restTemplate.getForEntity(endsWith(key), eq(String.class))).thenReturn(responseEntity);

    //WHEN
    String actual = searchClient.get(key);

    //THEN
    assertThat(actual, is("Error: Some Message"));
  }

  @Test
  public void shouldPutDocument() {
    //GIVEN
    String key = "key";
    String data = "data";
    ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.OK);
    HttpEntity<String> entity = createDocumentEntity(key, data);
    when(restTemplate.postForEntity(endsWith("documents"), eq(entity), eq(String.class))).thenReturn(responseEntity);

    //WHEN
    String actual = searchClient.put(key, data);

    //THEN
    assertThat(actual, is("Success:"));
  }

  @Test
  public void shouldGetErrorOnPutDocument() {
    //GIVEN
    String key = "key";
    String data = "data";
    ResponseEntity<String> responseEntity = new ResponseEntity<>("Some Message", HttpStatus.INTERNAL_SERVER_ERROR);
    HttpEntity<String> entity = createDocumentEntity(key, data);
    when(restTemplate.postForEntity(endsWith("documents"), eq(entity), eq(String.class))).thenReturn(responseEntity);

    //WHEN
    String actual = searchClient.put(key, data);

    //THEN
    assertThat(actual, is("Error: Some Message"));
  }

  @Test
  public void shouldSearch() {
    //GIVEN
    String tokens = "token";
    ResponseEntity<String> responseEntity = new ResponseEntity<>("key1 key2", HttpStatus.OK);
    when(restTemplate.getForEntity(endsWith("search?q="+tokens), eq(String.class))).thenReturn(responseEntity);

    //WHEN
    String actual = searchClient.search(tokens);

    //THEN
    assertThat(actual, is("Success: key1 key2"));
  }

  @Test
  public void shouldGetNotFoundMessageOnSearch() {
    //GIVEN
    String tokens = "token";
    ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    when(restTemplate.getForEntity(endsWith("search?q="+tokens), eq(String.class))).thenReturn(responseEntity);

    //WHEN
    String actual = searchClient.search(tokens);

    //THEN
    assertThat(actual, is("Documents not found"));
  }

  @Test
  public void shouldGetErrorOnSearch() {
    //GIVEN
    String tokens = "token";
    ResponseEntity<String> responseEntity = new ResponseEntity<>("Some Error", HttpStatus.INTERNAL_SERVER_ERROR);
    when(restTemplate.getForEntity(endsWith("search?q="+tokens), eq(String.class))).thenReturn(responseEntity);

    //WHEN
    String actual = searchClient.search(tokens);

    //THEN
    assertThat(actual, is("Error: Some Error"));
  }
}