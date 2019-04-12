package com.test.client.client;

import com.test.client.client.exception.NotFoundException;
import com.test.client.client.model.Document;

import java.util.Set;

public interface SearchClient {

  Set<String> search(String tokens) throws NotFoundException;

  void put(String key, String data) throws NotFoundException;

  Document get(String key) throws NotFoundException;

}
