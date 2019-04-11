package com.test.search.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Document {
  @NotEmpty
  private String key;
  @NotEmpty
  private String data;

}
