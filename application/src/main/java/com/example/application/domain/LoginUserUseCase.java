package com.example.application.domain;

import com.example.application.domain.model.User;

import javax.security.auth.login.FailedLoginException;

public interface LoginUserUseCase {
    public User login(String email, String password) throws FailedLoginException;
}
