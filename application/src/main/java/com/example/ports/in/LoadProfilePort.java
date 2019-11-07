package com.example.ports.in;

import com.example.Profile;
import com.example.User;

public interface LoadProfilePort {
  Profile loadProfile(String username);

  Boolean isFollowing(String username, User follower);
}
