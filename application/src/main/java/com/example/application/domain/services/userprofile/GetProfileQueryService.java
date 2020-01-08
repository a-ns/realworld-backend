package com.example.application.domain.services.userprofile;

import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.GetProfileQuery;
import com.example.application.domain.ports.out.LoadProfilePort;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class GetProfileQueryService implements GetProfileQuery {
  private LoadProfilePort profilePort;

  @Override
  public Profile getProfile(String username, Optional<User> request) {
    Boolean isFollowing = profilePort.isFollowing(username, request);
    Profile p = profilePort.loadProfile(username);
    p.setFollowing(isFollowing);
    return p;
  }
}
