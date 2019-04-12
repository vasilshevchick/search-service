package com.test.client.command;

import com.test.client.client.SearchClient;
import com.test.client.client.exception.NotFoundException;
import com.test.client.client.model.Document;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentCommandTest {

  @Mock
  private SearchClient client;
  @InjectMocks
  private DocumentCommand command;

  @Test
  public void shouldPut() {
    //GIVEN
    String key = "key";
    String data = "data";

    //WHEN
    String actual = command.put(key, data);

    //THEN
    assertThat(actual, is("Success:"));
  }

  @Test
  public void shouldGet() {
    //GIVEN
    String key = "key";
    String data = "Some Get Result";
    Document document = new Document(key, data);
    when(client.get(key)).thenReturn(document);

    //WHEN
    String actual = command.get(key);

    //THEN
    assertThat(actual, is("Success: " + document.toString()));
  }

  @Test
  public void shouldHandleNotFoundErrorOnGet() {
    //GIVEN
    String key = "key";
    when(client.get(key)).thenThrow(new NotFoundException());

    //WHEN
    String actual = command.get(key);

    //THEN
    assertThat(actual, is("Document not found"));
  }
}