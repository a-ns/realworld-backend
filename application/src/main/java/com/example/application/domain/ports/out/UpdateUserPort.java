package com.example.application.domain.ports.out;

import com.example.application.domain.model.User;

public interface UpdateUserPort {

  User save(User payload);
}
