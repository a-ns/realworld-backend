package com.example.adapters.persistence.comment;

import com.example.adapters.persistence.Audit;
import com.example.adapters.persistence.article.ArticleJpaEntity;
import com.example.application.domain.model.Article;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class CommentJpaEntity extends Audit {

  @Id private String id;
  @Column private String body;

  @Column private Integer authorId;

  @JoinColumn
  @ManyToOne
  private ArticleJpaEntity article;
}
