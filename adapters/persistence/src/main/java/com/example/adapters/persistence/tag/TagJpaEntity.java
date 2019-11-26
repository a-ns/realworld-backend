package com.example.adapters.persistence.tag;

import com.example.adapters.persistence.article.ArticleJpaEntity;
import java.util.List;
import javax.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table
@Data
@Builder
public class TagJpaEntity {
  @Id @GeneratedValue private Integer id;

  @Column(unique = true)
  private String tag;

  @ManyToMany(mappedBy = "tags")
  private List<ArticleJpaEntity> articles;
}
