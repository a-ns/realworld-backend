package com.example.adapters.persistence.user;

import com.example.adapters.persistence.userfollow.UserFollowRepository;
import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.out.GetUserPort;
import com.example.application.domain.ports.out.LoadProfilePort;
import com.example.application.domain.ports.out.SaveUserPort;
import com.example.application.domain.ports.out.UpdateUserPort;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class UserPersistenceAdapter implements GetUserPort, SaveUserPort, UpdateUserPort, LoadProfilePort {

  private UserRepository repository;
  private UserPersistenceMapper mapper;
  private UserFollowRepository followRepository;

  @Override
  public Optional<User> getUserByUsername(String username) {
    try {
      UserJpaEntity user = repository.findByUsername(username);
      if (user == null) {
        return Optional.empty();
      }
      return Optional.of(mapper.mapJpaEntityToDomain(user));
    } catch (EntityNotFoundException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    try {
      UserJpaEntity user = repository.findByEmail(email);
      if (user == null) return Optional.empty();
      return Optional.of(mapper.mapJpaEntityToDomain(user));
    } catch (EntityNotFoundException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> getUserById(Integer userId) {
    try {
      Optional<UserJpaEntity> user = repository.findById(userId);
      if (user.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(this.mapper.mapJpaEntityToDomain(user.get()));
    } catch (EntityNotFoundException e) {
      return Optional.empty();
    }
  }

  @Override
  public User saveUser(User user /* String username, String email, String password */) {
    UserJpaEntity saved =
        UserJpaEntity.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .username(user.getUsername())
            .build();
    return mapper.mapJpaEntityToDomain(repository.save(saved));
  }

  @Override
  public User save(User payload) {
    UserJpaEntity mapped = mapper.mapDomainToJpaEntity(payload);
    return mapper.mapJpaEntityToDomain(repository.save(mapped));
  }

  @Override
  public Optional<Profile> loadProfile(String username) {
    UserJpaEntity user = repository.findByUsername(username);
    if (user == null) {
      return Optional.empty();
    }
    return Optional.of(
        Profile.builder()
            .image(user.getImage())
            .bio(user.getBio())
            .following(false)
            .username(user.getUsername())
            .build());
  }

  @Override
  public Boolean isFollowing(String username, Optional<User> follower) {
    if (follower.isEmpty()) return false;
    Integer followedId = this.repository.findByUsername(username).getId();
    Integer followerId = this.repository.findByUsername(follower.get().getUsername()).getId();
    return this.followRepository.existsByFollowerAndFollowed(followerId, followedId);
  }
}
