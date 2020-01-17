package com.example.adapters.persistence.comment;

import com.example.application.domain.model.Comment;
import com.example.application.domain.model.CommentId;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
class CommentMapper {

  public Comment mapEntityToDomain(CommentJpaEntity entity) {
    return Comment.builder()
        .articleId(entity.getArticle().getId())
        .id(CommentId.builder().id(UUID.fromString(entity.getId())).build())
        .body(entity.getBody())
        .createAt(entity.getCreatedAt())
        .updated(entity.getUpdatedAt())
        .build();
  }
}
