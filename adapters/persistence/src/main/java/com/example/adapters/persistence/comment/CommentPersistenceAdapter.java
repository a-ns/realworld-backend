package com.example.adapters.persistence.comment;

import com.example.adapters.persistence.user.UserRepository;
import com.example.application.domain.model.Comment;
import com.example.application.domain.model.CommentId;
import com.example.application.domain.ports.out.DeleteCommentPort;
import com.example.application.domain.ports.out.LoadCommentPort;
import com.example.application.domain.ports.out.LoadProfilePort;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class CommentPersistenceAdapter implements DeleteCommentPort, LoadCommentPort {

  private CommentJpaRepository repository;
  private CommentMapper mapper;
  private LoadProfilePort profilePort;
  private UserRepository userRepository;

  @Override
  public Comment delete(CommentId input) {
    Optional<CommentJpaEntity> entity = this.repository.findById(input.getId().toString());
    if (entity.isEmpty()) return Comment.builder().build();
    Comment comment = mapper.mapEntityToDomain(entity.get());
    fillAuthor(entity, comment);
    this.repository.deleteById(input.getId().toString());
    return comment;
  }

  private void fillAuthor(Optional<CommentJpaEntity> entity, Comment comment) {
    comment.setAuthor(
        profilePort
            .loadProfile(userRepository.findById(entity.get().getAuthorId()).get().getUsername())
            .get());
  }

  @Override
  public Optional<Comment> getComment(CommentId id) {
    Optional<CommentJpaEntity> entity = this.repository.findById(id.getId().toString());
    if (entity.isEmpty()) return Optional.empty();
    Comment comment = this.mapper.mapEntityToDomain(entity.get());
    fillAuthor(entity, comment);
    return Optional.of(comment);
  }

  @Override
  public Collection<Comment> getComments(String slug) {
    Map<CommentJpaEntity, Comment> comments = new HashMap<>();
    this.repository
        .findByArticleSlug(slug)
        .forEach(
            (entity) -> {
              comments.put(entity, mapper.mapEntityToDomain(entity));
            });
    comments.forEach(
        (entity, comment) -> {
          fillAuthor(Optional.of(entity), comment);
        });
    return comments.values();
  }
}
