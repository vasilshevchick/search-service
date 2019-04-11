package com.test.search.service.impl;

import com.test.search.service.DocumentService;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class DocumentServiceImpl implements DocumentService {

  private final ConcurrentHashMap<String, String> documents = new ConcurrentHashMap<>();

  @Override
  public String get(String key) {
    return documents.get(key);
  }

  @Override
  public void put(String key, String data) {
    documents.put(key, data);
  }
}
