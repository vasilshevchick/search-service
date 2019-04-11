package com.test.search.service;

import java.util.Set;

public interface SearchService {

  Set<String> search(String tokens);
  void index(String key, String data);
}
