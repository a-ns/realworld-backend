package com.example.adapters.persistence.comment;

import com.example.application.domain.model.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentJpaEntity, String> {

  List<Comment> findByArticleSlug(String slug);
}
