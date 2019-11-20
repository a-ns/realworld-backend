package com.example.application.domain.ports.in;

import com.example.application.domain.model.User;
import com.example.application.domain.model.Profile;

public interface LoadProfilePort {
  Profile loadProfile(String username);

  Boolean isFollowing(String username, User follower);
}
