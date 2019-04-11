package com.test.search.service.impl;

import com.test.search.service.SearchService;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InvertedIndexSearchService implements SearchService {

  private final ConcurrentHashMap<String, Set<String>> index = new ConcurrentHashMap<>();

  @Override
  public Set<String> search(String tokens) {

    Set<String> tokensSet = Stream.of(tokens.split("\\s+"))
                                  .filter(s -> !s.isEmpty())
                                  .map(String::toLowerCase)
                                  .collect(Collectors.toSet());
    return tokensSet.stream()
                    .map(index::get)
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() == tokensSet.size())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
  }

  @Override
  public void index(String key, String data) {
    Stream.of(data.split("\\s+"))
          .filter(s -> !s.isEmpty())
          .map(String::toLowerCase)
          .forEach(word -> {
            Set<String> ids = index.computeIfAbsent(word, k -> Collections.newSetFromMap(new ConcurrentHashMap<>()));
            ids.add(key);
          });
  }
}
