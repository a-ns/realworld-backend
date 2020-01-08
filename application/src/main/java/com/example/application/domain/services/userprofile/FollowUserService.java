package com.example.application.domain.services.userprofile;

import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.FollowUserUseCase;
import com.example.application.domain.ports.in.GetProfileQuery;
import com.example.application.domain.ports.out.SaveFollowRelationPort;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class FollowUserService implements FollowUserUseCase {
  private final SaveFollowRelationPort followUserPort;
  private final GetProfileQuery getProfileQuery;

  @Override
  public Profile follow(User followed, User follower) {
    Profile followedProfile =
        getProfileQuery.getProfile(followed.getUsername(), Optional.of(follower));
    if (followedProfile.getFollowing()) {
      return followedProfile;
    }
    this.followUserPort.saveFollowRelation(followed, follower);
    followedProfile.setFollowing(true);
    return followedProfile;
  }

  @Override
  public Profile unfollow(User followed, User follower) {
    Profile followedProfile =
        getProfileQuery.getProfile(followed.getUsername(), Optional.of(follower));
    if (!followedProfile.getFollowing()) {
      return followedProfile;
    }
    this.followUserPort.removeFollowRelation(followed, follower);
    followedProfile.setFollowing(false);
    return followedProfile;
  }
}
