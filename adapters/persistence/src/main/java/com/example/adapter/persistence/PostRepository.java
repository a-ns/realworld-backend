package com.example.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface PostRepository extends JpaRepository<PostJpaEntity, Integer> {}
