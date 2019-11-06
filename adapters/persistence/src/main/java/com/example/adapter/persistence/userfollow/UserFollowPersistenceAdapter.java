package com.example.adapter.persistence.userfollow;

import com.example.Profile;
import com.example.User;
import com.example.adapter.persistence.user.UserJpaEntity;
import com.example.adapter.persistence.user.UserRepository;
import com.example.ports.out.FollowUserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
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
