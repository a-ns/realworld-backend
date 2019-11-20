package com.example.adapters.web.jwt;

import com.example.application.domain.model.User;

import java.util.Optional;

public interface JwtService {

  String toToken(User user);

  Optional<String> getSubFromToken(String token);
}
