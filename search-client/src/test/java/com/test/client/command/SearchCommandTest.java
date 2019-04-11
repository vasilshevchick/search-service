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
public class SearchCommandTest {

  @Mock
  private SearchClient client;
  @InjectMocks
  private SearchCommand command;

  @Test
  public void search() {
    //GIVEN
    String tokens = "tokens";
    when(client.search(tokens)).thenReturn("Some Search Result");

    //WHEN
    String actual = command.search(tokens);

    //THEN
    assertThat(actual, is("Some Search Result"));
  }
}