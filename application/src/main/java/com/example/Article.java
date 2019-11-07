package com.example;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Article {
  private Integer id;
  private String slug;
  private String title;
  private String description;
  private String body;
  private List<String> tags;
  private Boolean favorited;
  private Integer favoritesCount;
  private Profile author;
}
