package com.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Author {
  private String username;
  private String bio;
  private String image;
  private Boolean following;
}
