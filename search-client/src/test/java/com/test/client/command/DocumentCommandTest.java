package com.test.client.command;

import com.test.client.client.SearchClient;

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
  public void put() {
    //GIVEN
    String key = "key";
    String data = "data";
    when(client.put(key, data)).thenReturn("Some Put Result");

    //WHEN
    String actual = command.put(key, data);

    //THEN
    assertThat(actual, is("Some Put Result"));
  }

  @Test
  public void get() {
    //GIVEN
    String key = "key";
    when(client.get(key)).thenReturn("Some Get Result");

    //WHEN
    String actual = command.get(key);

    //THEN
    assertThat(actual, is("Some Get Result"));
  }
}