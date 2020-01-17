package com.example.adapters.persistence.comment;

import com.example.application.domain.model.Comment;
import com.example.application.domain.model.CommentId;
import com.example.application.domain.ports.out.DeleteCommentPort;
import com.example.application.domain.ports.out.LoadCommentPort;
import java.util.Collection;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class CommentPersistenceAdapter implements DeleteCommentPort, LoadCommentPort {

  private CommentJpaRepository repository;
  private CommentMapper mapper;

  @Override
  public Comment delete(CommentId input) {
    Optional<CommentJpaEntity> entity = this.repository.findById(input.getId().toString());
    if (entity.isEmpty()) return Comment.builder().build();
    Comment comment = mapper.mapEntityToDomain(entity.get());
    this.repository.deleteById(input.getId().toString());
    return comment;
  }

  @Override
  public Optional<Comment> getComment(CommentId id) {
    Optional<CommentJpaEntity> entity = this.repository.findById(id.getId().toString());
    if (entity.isEmpty()) return Optional.empty();
    return Optional.of(this.mapper.mapEntityToDomain(entity.get()));
  }

  @Override
  public Collection<Comment> getComments(String slug) {
    return this.repository.findByArticleSlug(slug);
  }
}
