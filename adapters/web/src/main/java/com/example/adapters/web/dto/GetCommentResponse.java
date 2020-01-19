package com.example.adapters.web.dto;

import com.example.application.domain.model.Comment;
import com.example.application.domain.model.Profile;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentResponse implements Serializable {

  private Body comment;

  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  @Builder
  public static class Body {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String body;
    private Profile author;
  }

  public static GetCommentResponse from(Comment comment) {
    return GetCommentResponse.builder()
        .comment(
            Body.builder()
                .author(comment.getAuthor())
                .body(comment.getBody())
                .createdAt(comment.getCreateAt())
                .updatedAt(comment.getUpdated())
                .id(comment.getId().getId())
                .build())
        .build();
  }
}
