package com.example.adapter.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "post")
class PostJpaEntity {

  @Id private int id;

  @Column private String title;

  @Column private String text;

  @Column private String category;

  @Column private String author;
}
