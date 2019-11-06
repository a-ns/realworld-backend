package com.example.adapter.persistence.userfollow;

import com.example.adapter.persistence.user.UserJpaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserFollowRepository extends JpaRepository<UserFollowJpaEntity, Integer> {

  @Query(
      "select a from UserJpaEntity a where a.id in (select id from UserFollowJpaEntity b where b.followed = ?1)")
  List<UserJpaEntity> getFollowers(Integer userId);

  Boolean existsByFollowerAndFollowed(Integer follower, Integer followed);

  UserFollowJpaEntity findByFollowerAndFollowed(Integer follower, Integer followed);
}
