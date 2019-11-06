package com.example.adapter.persistence.user;

import javax.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "user_e")
public class UserJpaEntity {

  @Id @GeneratedValue private int id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  @Column private String password;

  @Column private String bio;

  @Column private String image;
}
