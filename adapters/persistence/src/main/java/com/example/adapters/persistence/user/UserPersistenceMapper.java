package com.example.adapters.persistence.user;

import com.example.application.domain.model.User;
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
            .id(user.getId())
        .build();
  }

  public UserJpaEntity mapDomainToJpaEntity(User user) {
    return UserJpaEntity.builder()
        .bio(user.getBio())
        .email(user.getEmail())
        .image(user.getImage())
        .password(user.getPassword())
        .username(user.getUsername())
            .id(user.getId())
        .build();
  }
}
