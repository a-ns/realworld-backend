package com.example.adapter.persistence.user;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "user_e")
class UserJpaEntity {

  @Id @GeneratedValue private int id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  @Column private String password;

  @Column private String bio;

  @Column private String image;
}
