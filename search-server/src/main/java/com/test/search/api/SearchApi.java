package com.test.search.api;

import com.test.search.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/search")
public class SearchApi {

  private final SearchService searchService;

  @Autowired
  public SearchApi(SearchService searchService) {this.searchService = searchService;}

  @GetMapping()
  public ResponseEntity<Set<String>> search(@RequestParam("q") @NotEmpty String query) {
    Set<String> result = searchService.search(query);
    if (result.isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(result);
    }
  }
}
