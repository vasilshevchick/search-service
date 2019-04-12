package com.test.client.command;

import com.test.client.client.SearchClient;
import com.test.client.client.exception.NotFoundException;
import com.test.client.client.model.Document;

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
    client.put(key, data);
    return "Success:";
  }

  @ShellMethod("Get document")
  public String get(@ShellOption String key) {
    String result;
    try {
      Document document = client.get(key);
      result = "Success: " + document.toString();
    } catch (NotFoundException e) {
      return "Document not found";
    }
    return result;
  }
}
