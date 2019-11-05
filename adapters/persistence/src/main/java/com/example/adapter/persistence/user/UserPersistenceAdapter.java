package com.example.adapter.persistence.user;

import com.example.User;
import com.example.ports.in.GetUserPort;
import com.example.ports.out.RegisterUserPort;
import com.example.ports.out.UpdateUserPort;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class UserPersistenceAdapter implements GetUserPort, RegisterUserPort, UpdateUserPort {

  private UserRepository repository;
  private UserPersistenceMapper mapper;

  @Override
  public Optional<User> getUserByUsername(String username) {
    try {

      return Optional.of(mapper.mapJpaEntityToDomain(repository.findByUsername(username)));
    } catch (EntityNotFoundException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    try {
      return Optional.of(mapper.mapJpaEntityToDomain(repository.findByEmail(email)));
    } catch (EntityNotFoundException e) {
      return Optional.empty();
    }
  }

  @Override
  public User registerUser(String username, String email, String password) {
    UserJpaEntity user =
        UserJpaEntity.builder().email(email).password(password).username(username).build();
    return mapper.mapJpaEntityToDomain(repository.save(user));
  }

  @Override
  public User save(String previousUsername, User payload) {
    UserJpaEntity found = this.repository.findByUsername(previousUsername);
    UserJpaEntity mapped = mapper.mapDomainToJpaEntity(payload);
    mapped.setId(found.getId());
    return mapper.mapJpaEntityToDomain(repository.save(mapped));
  }
}
