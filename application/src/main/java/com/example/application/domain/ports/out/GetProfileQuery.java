package com.example.application.domain.ports.out;

import com.example.application.domain.model.User;
import com.example.application.domain.model.Profile;

import java.util.Optional;

public interface GetProfileQuery {
  Profile getProfile(String username, Optional<User> request);
}
