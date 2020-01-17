package com.example.adapters.persistence.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentJpaEntity, String> {

  List<CommentJpaEntity> findByArticleSlug(String slug);
}
