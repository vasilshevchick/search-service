package com.test.client.command;

import com.test.client.client.SearchClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class SearchCommand {

  private final SearchClient client;

  @Autowired
  public SearchCommand(SearchClient client) {
    this.client = client;
  }

  @ShellMethod("Search tokens")
  public String search(@ShellOption String tokens) {
    return client.search(tokens);
  }
}
