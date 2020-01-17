package com.example.adapters.persistence.comment;

import com.example.adapters.persistence.Audit;
import com.example.application.domain.model.Article;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CommentJpaEntity extends Audit {

  @Id private String id;
  @Column private String body;

  @Column private Integer authorId;

  @Column private Article article;
}
