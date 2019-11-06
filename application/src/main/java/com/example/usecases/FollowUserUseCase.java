package com.example.usecases;

import com.example.Profile;
import com.example.User;

public interface FollowUserUseCase {
  Profile follow(User followed, User follower);

  Profile unfollow(User followed, User follower);
}
