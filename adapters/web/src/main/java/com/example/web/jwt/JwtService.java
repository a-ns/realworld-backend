package com.example.web.jwt;

import com.example.User;
import java.util.Optional;

public interface JwtService {

  String toToken(User user);

  Optional<String> getSubFromToken(String token);
}
