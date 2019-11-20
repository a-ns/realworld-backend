package com.example.adapters.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PostUpdateInput {
  private String text;
  private String author;
  private String category;
  private String title;
}
