package com.test.client.client;

import com.test.client.client.model.Document;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.test.client.util.Util.createDocumentEntity;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchClientTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Mock
  private RestTemplate restTemplate;
  @InjectMocks
  private SearchClientImpl searchClient;

  @Test
  public void shouldGetDocumentSuccessfully() {
    //GIVEN
    String key = "key";
    String data = "DocumentData";
    Document document = new Document(key, data);
    ResponseEntity<Document> responseEntity = new ResponseEntity<>(document, HttpStatus.OK);
    when(restTemplate.getForEntity(endsWith(key), eq(Document.class))).thenReturn(responseEntity);

    //WHEN
    Document actual = searchClient.get(key);

    //THEN
    assertThat(actual, is(document));
  }

  @Test
  public void shouldGetNotFoundErrorOnGet() {
    //GIVEN
    String key = "key";
    when(restTemplate.getForEntity(endsWith(key), eq(Document.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

    //THEN
    thrown.expect(RestClientException.class);
    thrown.expectMessage("404 NOT_FOUND");

    //WHEN
    searchClient.get(key);
  }

  @Test
  public void shouldGetErrorOnGet() {
    //GIVEN
    String key = "key";
    when(restTemplate.getForEntity(endsWith(key), eq(Document.class))).thenThrow(new RestClientException("Some Error"));

    //THEN
    thrown.expect(RestClientException.class);
    thrown.expectMessage("Some Error");

    //WHEN
    searchClient.get(key);
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
    searchClient.put(key, data);

    //THEN
    verify(restTemplate).postForEntity(endsWith("documents"), eq(entity), eq(String.class));
    verifyNoMoreInteractions(restTemplate);
  }

  @Test
  public void shouldGetErrorOnPutDocument() {
    //GIVEN
    String key = "key";
    String data = "data";
    ResponseEntity<String> responseEntity = new ResponseEntity<>("Some Message", HttpStatus.INTERNAL_SERVER_ERROR);
    HttpEntity<String> entity = createDocumentEntity(key, data);
    when(restTemplate.postForEntity(endsWith("documents"), eq(entity), eq(String.class)))
        .thenThrow(new RestClientException("Some Error"));

    //THEN
    thrown.expect(RestClientException.class);
    thrown.expectMessage("Some Error");

    //WHEN
    searchClient.put(key, data);
  }

  @Test
  public void shouldSearch() {
    //GIVEN
    String tokens = "token";
    Set<String> searchResult = Stream.of("key1", "key2").collect(Collectors.toSet());
    ResponseEntity<Set<String>> responseEntity = new ResponseEntity<>(searchResult, HttpStatus.OK);
    when(restTemplate.exchange(
        endsWith("search?q=" + tokens),
        eq(HttpMethod.GET), eq(null),
        ArgumentMatchers.<ParameterizedTypeReference<Set<String>>>any()))
        .thenReturn(responseEntity);

    //WHEN
    Set<String> actual = searchClient.search(tokens);

    //THEN
    assertThat(actual, is(searchResult));
  }

  @Test
  public void shouldGetNotFoundMessageOnSearch() {
    //GIVEN
    String tokens = "token";
    ResponseEntity<String> responseEntity = new ResponseEntity<>("", HttpStatus.NOT_FOUND);
    when(restTemplate.exchange(
        endsWith("search?q=" + tokens),
        eq(HttpMethod.GET), eq(null),
        ArgumentMatchers.<ParameterizedTypeReference<Set<String>>>any()))
        .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

    //THEN
    thrown.expect(HttpClientErrorException.class);
    thrown.expectMessage("404 NOT_FOUND");

    //WHEN
    searchClient.search(tokens);
  }

  @Test
  public void shouldGetErrorOnSearch() {
    //GIVEN
    String tokens = "token";
    when(restTemplate.exchange(
        endsWith("search?q=" + tokens),
        eq(HttpMethod.GET), eq(null),
        ArgumentMatchers.<ParameterizedTypeReference<Set<String>>>any()))
        .thenThrow(new RestClientException("Some Error"));

    //THEN
    thrown.expect(RestClientException.class);
    thrown.expectMessage("Some Error");

    //WHEN
    searchClient.search(tokens);

  }
}