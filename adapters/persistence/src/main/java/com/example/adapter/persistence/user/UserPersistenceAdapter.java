package com.example.adapter.persistence.user;

import com.example.User;
import com.example.ports.in.GetUserPort;
import com.example.ports.out.RegisterUserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class UserPersistenceAdapter implements GetUserPort, RegisterUserPort {

  private UserRepository repository;
  private UserPersistenceMapper mapper;

  @Override
  public User getUserByUsername(String username) {
    return mapper.mapJpaEntityToDomain(repository.findByUsername(username));
  }

  @Override
  public User getUserByEmail(String email) {
    return mapper.mapJpaEntityToDomain(repository.findByEmail(email));
  }

  @Override
  public User registerUser(String username, String email, String password) {
    UserJpaEntity user =
        UserJpaEntity.builder().email(email).password(password).username(username).build();
    return mapper.mapJpaEntityToDomain(repository.save(user));
  }
}
