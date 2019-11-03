package com.example;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Comment {
  private LocalDateTime createAt;
  private LocalDateTime updated;
  private String body;
  private Author author;
}
