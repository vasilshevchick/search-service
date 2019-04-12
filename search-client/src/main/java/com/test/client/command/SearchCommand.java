package com.test.client.command;

import com.test.client.client.SearchClient;
import com.test.client.client.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Set;

@ShellComponent
public class SearchCommand {

  private final SearchClient client;

  @Autowired
  public SearchCommand(SearchClient client) {
    this.client = client;
  }

  @ShellMethod("Search tokens")
  public String search(@ShellOption String tokens) {
    String result;
    try {
      Set<String> searchResult = client.search(tokens);
      result = "Success: " + String.join(",", searchResult);
    } catch (NotFoundException e) {
      return "Tokens not found";
    }
    return result;
  }
}
