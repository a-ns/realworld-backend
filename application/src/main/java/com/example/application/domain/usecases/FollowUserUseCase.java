package com.example.application.domain.usecases;

import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;

public interface FollowUserUseCase {
  Profile follow(User followed, User follower);

  Profile unfollow(User followed, User follower);
}
