package com.example.adapters.web;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootConfiguration
public class BCryptPasswordEncoderConfiguration {

  @Bean
  public PasswordEncoder settings() {
    return new BCryptPasswordEncoder();
  }
}
