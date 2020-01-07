package com.example.application.domain.services.userprofile;

import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.FollowUserUseCase;
import com.example.application.domain.ports.out.SaveFollowRelationPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class FollowUserService implements FollowUserUseCase {
  private final SaveFollowRelationPort followUserPort;

  @Override
  public Profile follow(User followed, User follower) {
    return this.followUserPort.saveFollowRelation(followed, follower);
  }

  @Override
  public Profile unfollow(User followed, User follower) {
    return this.followUserPort.removeFollowRelation(followed, follower);
  }
}
