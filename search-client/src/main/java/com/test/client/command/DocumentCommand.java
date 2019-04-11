package com.test.client.command;

import com.test.client.client.SearchClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class DocumentCommand {

  private final SearchClient client;

  @Autowired
  public DocumentCommand(SearchClient client) {this.client = client;}

  @ShellMethod("Put document")
  public String put(@ShellOption String key, @ShellOption String data) {
    return client.put(key, data);
  }

  @ShellMethod("Get document")
  public String get(@ShellOption String key) {
    return client.get(key);
  }
}
