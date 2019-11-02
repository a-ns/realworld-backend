package com.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Post {
  private int id;
  private String title;
  private String text;
  private String category;
  private String author;
}
