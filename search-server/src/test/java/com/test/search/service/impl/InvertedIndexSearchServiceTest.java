package com.test.search.service.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InvertedIndexSearchServiceTest {

  private InvertedIndexSearchService service;

  @Before
  public void setUp() {
    service = new InvertedIndexSearchService();
  }

  @Test
  public void search() {
    //GIVEN
    service.index("d1", " w1 w2  ");
    service.index("d2", "w1 w3");
    service.index("d3", "w1 w2 w3");
    String query = "    w1  w2 ";

    //WHEN
    Set<String> actual = service.search(query);
    Set<String> expected = new HashSet<>();
    expected.add("d1");
    expected.add("d3");
    assertThat(actual, is(expected));
  }

//  @Test
//  public void shouldPutAndGet() {
//    //GIVEN
//    String key = "key";
//    String value = "value1 value2 value3";
//    //WHEN
//    service.index(key, value);
//    //THEN
//    assertThat(service.get(key), is(value));
//  }
}