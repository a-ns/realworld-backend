package com.example.ports.out;

import com.example.User;

public interface UpdateUserPort {

  User save(String previousUsername, User payload);
}
