package com.example.ports.in;

import com.example.User;

public interface UpdateUserPort {

  User save(String previousUsername, User payload);
}
