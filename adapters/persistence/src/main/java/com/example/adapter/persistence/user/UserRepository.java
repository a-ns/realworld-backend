package com.example.adapter.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<UserJpaEntity, Integer> {

  UserJpaEntity findByUsername(String username);

  UserJpaEntity findByEmail(String email);
}
