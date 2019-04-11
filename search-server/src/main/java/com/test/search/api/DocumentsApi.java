package com.test.search.api;

import com.test.search.model.Document;
import com.test.search.service.DocumentService;
import com.test.search.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/documents")
public class DocumentsApi {

  private final DocumentService documentService;
  private final SearchService searchService;

  @Autowired
  public DocumentsApi(DocumentService documentService, SearchService searchService) {this.documentService = documentService;
    this.searchService = searchService;
  }

  @PostMapping
  public ResponseEntity addDocument(@RequestBody @Valid Document document) {
    documentService.put(document.getKey(), document.getData());
    searchService.index(document.getKey(), document.getData());
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{key}")
  public ResponseEntity<Document> getDocument(@PathVariable String key) {
    String data = documentService.get(key);
    return Optional.ofNullable(data)
                   .map(d -> ResponseEntity.ok(new Document(key, data)))
                   .orElse(ResponseEntity.notFound().build());
  }
}
