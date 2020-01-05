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
  public User registerUser(String username, String email, String password) {
    UserJpaEntity user =
        UserJpaEntity.builder().email(email).password(password).username(username).build();
    return mapper.mapJpaEntityToDomain(repository.save(user));
  }

  @Override
  public User save(Integer userId, User payload) {
    UserJpaEntity found = this.repository.findById(userId).get();
    UserJpaEntity mapped = mapper.mapDomainToJpaEntity(payload);
    mapped.setId(found.getId());
    return mapper.mapJpaEntityToDomain(repository.save(mapped));
  }

  @Override
  public Profile loadProfile(String username) {
    UserJpaEntity user = repository.findByUsername(username);
    return Profile.builder()
        .image(user.getImage())
        .bio(user.getBio())
        .following(false)
        .username(user.getUsername())
        .build();
  }

  @Override
  public Boolean isFollowing(String username, User follower) {
    if (follower == null) return false;
    Integer followedId = this.repository.findByUsername(username).getId();
    Integer followerId = this.repository.findByUsername(follower.getUsername()).getId();
    return this.followRepository.existsByFollowerAndFollowed(followerId, followedId);
  }
}
