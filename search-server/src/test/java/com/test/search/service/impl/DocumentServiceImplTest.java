package com.test.search.service.impl;

import com.test.search.service.DocumentService;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DocumentServiceImplTest {

  private DocumentService service;

  @Before
  public void setUp() {
    service = new DocumentServiceImpl();
  }

  @Test
    public void shouldPutAndGet() {
      //GIVEN
      String key = "key";
      String value = "value1 value2 value3";
      //WHEN
      service.put(key, value);
      //THEN
      assertThat(service.get(key), is(value));
    }
}