package com.example.adapter.persistence.user;

import com.example.User;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {
  public User mapJpaEntityToDomain(UserJpaEntity user) {
    return User.builder()
        .bio(user.getBio())
        .email(user.getEmail())
        .image(user.getImage())
        .password(user.getPassword())
        .username(user.getUsername())
        .build();
  }

  public UserJpaEntity mapDomainToJpaEntity(User user) {
    return UserJpaEntity.builder()
        .bio(user.getBio())
        .email(user.getEmail())
        .image(user.getImage())
        .password(user.getPassword())
        .username(user.getUsername())
        .build();
  }
}
