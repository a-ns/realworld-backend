package com.example.adapter.persistence.user;

import com.example.User;
import org.springframework.stereotype.Component;

@Component
class UserPersistenceMapper {
  public User mapJpaEntityToDomain(UserJpaEntity user) {
    return User.builder()
        .bio(user.getBio())
        .email(user.getEmail())
        .image(user.getImage())
        .password(user.getPassword())
        .username(user.getUsername())
        .build();
  }
}
