package com.example.application.domain.ports.in;

import com.example.application.domain.model.User;

public interface UpdateUserPort {

  User save(Integer userId, User payload);
}