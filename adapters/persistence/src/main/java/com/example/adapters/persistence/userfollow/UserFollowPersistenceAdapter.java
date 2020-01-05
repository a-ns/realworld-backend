package com.example.adapters.persistence.userfollow;

import com.example.adapters.persistence.user.UserJpaEntity;
import com.example.adapters.persistence.user.UserRepository;
import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.out.FollowUserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
// TODO all this code should be in (application) module
class UserFollowPersistenceAdapter implements FollowUserPort {

  private UserRepository userRepository;
  private UserFollowRepository followRepository;

  @Override
  public Profile saveFollowRelation(User followed, User follower) {
    UserJpaEntity followedEntity = userRepository.findByEmail(followed.getEmail());
    UserJpaEntity followerEntity = userRepository.findByEmail(follower.getEmail());
    if (followRepository.existsByFollowerAndFollowed(
        followerEntity.getId(), followedEntity.getId())) {
      return Profile.builder()
          .username(followed.getUsername())
          .following(true)
          .bio(followed.getBio())
          .image(followed.getImage())
          .build();
    }
    followRepository.save(
        UserFollowJpaEntity.builder()
            .follower(followerEntity.getId())
            .followed(followedEntity.getId())
            .build());
    return Profile.builder()
        .username(followed.getUsername())
        .following(true)
        .bio(followed.getBio())
        .image(followed.getImage())
        .build();
  }

  @Override
  public Profile removeFollowRelation(User followed, User follower) {
    UserJpaEntity followedEntity = userRepository.findByEmail(followed.getEmail());
    UserJpaEntity followerEntity = userRepository.findByEmail(follower.getEmail());
    if (!followRepository.existsByFollowerAndFollowed(
        followerEntity.getId(), followedEntity.getId())) {
      return Profile.builder()
          .username(followed.getUsername())
          .following(false)
          .bio(followed.getBio())
          .image(followed.getImage())
          .build();
    }
    followRepository.delete(
        followRepository.findByFollowerAndFollowed(followerEntity.getId(), followedEntity.getId()));
    return Profile.builder()
        .username(followed.getUsername())
        .following(false)
        .bio(followed.getBio())
        .image(followed.getImage())
        .build();
  }
}
