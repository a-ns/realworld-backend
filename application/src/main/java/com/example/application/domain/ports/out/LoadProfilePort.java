package com.example.application.domain.ports.out;

import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;
import java.util.Optional;

public interface LoadProfilePort {
  Profile loadProfile(String username);

  Boolean isFollowing(String username, Optional<User> follower);
}
