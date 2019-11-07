package com.example.ports.out;

import com.example.Profile;
import com.example.User;
import java.util.Optional;

public interface GetProfileQuery {
  Profile getProfile(String username, Optional<User> request);
}
