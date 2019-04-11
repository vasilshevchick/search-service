package com.test.search.api;

import com.test.search.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

import static com.test.search.api.TestUtil.createEntity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchApiITTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void shouldSearch() {
    //GIVEN
    HttpEntity<String> entity1 = createEntity("{\"key\": \"key1\", \"data\": \"data1 data2\"}");
    HttpEntity<String> entity2 = createEntity("{\"key\": \"key2\", \"data\": \"data2 data3\"}");
    HttpEntity<String> entity3 = createEntity("{\"key\": \"key3\", \"data\": \"data3 data1\"}");
    Stream.of(entity1, entity2, entity3)
          .forEach(entity -> restTemplate.postForObject("/documents", entity, String.class));

    //WHEN
    ResponseEntity<String> responseEntity = restTemplate.getForEntity("/search?q=data1", String.class);

    //THEN
    assertThat(responseEntity.getStatusCodeValue(), is(200));
    assertThat(responseEntity.getBody(), is("[\"key1\",\"key3\"]"));
  }

  @Test
  public void shouldGetNotFoundSearch() {
    //GIVEN
    HttpEntity<String> entity1 = createEntity("{\"key\": \"key1\", \"data\": \"data1 data2\"}");
    HttpEntity<String> entity2 = createEntity("{\"key\": \"key2\", \"data\": \"data2 data3\"}");
    HttpEntity<String> entity3 = createEntity("{\"key\": \"key3\", \"data\": \"data3 data1\"}");
    Stream.of(entity1, entity2, entity3)
          .forEach(entity -> restTemplate.postForObject("/documents", entity, String.class));

    //WHEN
    ResponseEntity<String> responseEntity = restTemplate.getForEntity("/search?q=data", String.class);

    //THEN
    assertThat(responseEntity.getStatusCodeValue(), is(404));
  }

  @Test
  public void shouldGetBadRequestOnEmptyQuery() {
    //WHEN
    ResponseEntity<String> responseEntity = restTemplate.getForEntity("/search", String.class);

    //THEN
    assertThat(responseEntity.getStatusCodeValue(), is(400));
  }

}