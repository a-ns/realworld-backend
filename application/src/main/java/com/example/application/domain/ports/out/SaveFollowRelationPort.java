package com.example.application.domain.ports.out;

import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;

public interface SaveFollowRelationPort {
  Profile saveFollowRelation(User followed, User follower);

  Profile removeFollowRelation(User followed, User follower);
}
