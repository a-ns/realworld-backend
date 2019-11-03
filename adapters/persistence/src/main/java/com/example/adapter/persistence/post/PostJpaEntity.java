package com.example.adapter.persistence.post;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.Wither;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "post")
class PostJpaEntity {

  @Wither @Id @GeneratedValue private int id;

  @Column private String title;

  @Column private String text;

  @Column private String category;

  @Column private String author;
}
