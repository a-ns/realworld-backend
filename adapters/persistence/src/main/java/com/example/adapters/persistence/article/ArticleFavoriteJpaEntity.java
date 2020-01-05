package com.example.adapters.persistence.article;

import javax.persistence.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
@Table
class ArticleFavoriteJpaEntity {

  @Id @GeneratedValue private Integer id;
  @Column private Integer userId;
  @Column private Integer articleId;
}
