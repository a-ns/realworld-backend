package com.example.application.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Comment {
  private String id;
  private LocalDateTime createAt;
  private LocalDateTime updated;
  private String body;
  private Profile author;
  private Integer articleId;


}
