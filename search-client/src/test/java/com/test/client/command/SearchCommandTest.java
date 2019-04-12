package com.test.client.command;

import com.test.client.client.SearchClient;
import com.test.client.client.exception.NotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
  public void shouldSearch() {
    //GIVEN
    String tokens = "tokens";
    Set<String> searchResult = Stream.of("key1", "key2").collect(Collectors.toSet());
    when(client.search(tokens)).thenReturn(searchResult);

    //WHEN
    String actual = command.search(tokens);

    //THEN
    assertThat(actual, is("Success: key1,key2"));
  }

  @Test
  public void shouldHandleNotFoundErrorOnSearch() {
    //GIVEN
    String tokens = "tokens";
    when(client.search(tokens)).thenThrow(new NotFoundException());

    //WHEN
    String actual = command.search(tokens);

    //THEN
    assertThat(actual, is("Tokens not found"));
  }
}