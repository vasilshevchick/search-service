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

import static com.test.search.api.TestUtil.createEntity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentsApiITTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void shouldAddAndGetDocument() {
    //GIVEN
    HttpEntity<String> entity = createEntity("{\"key\": \"key1\", \"data\": \"data1 data2\"}");
    restTemplate.postForObject("/documents", entity, String.class);

    //WHEN
    ResponseEntity<String> responseEntity = restTemplate.getForEntity("/documents/key1", String.class);

    //THEN
    assertThat(responseEntity.getStatusCodeValue(), is(200));
    assertThat(responseEntity.getBody(), is("{\"key\":\"key1\",\"data\":\"data1 data2\"}"));
  }

  @Test
  public void shortGetNotFoundError() {
   //WHEN
    ResponseEntity<String> responseEntity = restTemplate.getForEntity("/documents/somekey", String.class);

    //THEN
    assertThat(responseEntity.getStatusCodeValue(), is(404));
  }

  @Test
  public void shortGetBadRequestError() {
    //WHEN
    HttpEntity<String> entity = createEntity("{\"key\": \"\", \"data\": \"\"}");
    ResponseEntity<String> responseEntity = restTemplate.postForEntity("/documents", entity, String.class);

    //THEN
    assertThat(responseEntity.getStatusCodeValue(), is(400));
  }
}