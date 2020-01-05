package com.example.application.domain.ports.in;

import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;
import java.util.Optional;

public interface GetProfileQuery {
  Profile getProfile(String username, Optional<User> request);
}
