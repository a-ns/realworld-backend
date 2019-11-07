package com.example.ports.in;

import com.example.Profile;
import com.example.User;

public interface FollowUserPort {

  Profile saveFollowRelation(User followed, User follower);

  Profile removeFollowRelation(User followed, User follower);
}
