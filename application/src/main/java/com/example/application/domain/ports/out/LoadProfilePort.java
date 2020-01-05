package com.example.application.domain.ports.out;

import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;

public interface LoadProfilePort {
  Profile loadProfile(String username);

  Boolean isFollowing(String username, User follower);
}
