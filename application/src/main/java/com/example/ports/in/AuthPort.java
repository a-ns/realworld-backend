package com.example.ports.in;

import com.example.User;
import javax.security.auth.login.LoginException;

public interface AuthPort {

  String login(String email, String password) throws LoginException;

  String encrypt(String password);

  String generateToken(User user);
}
