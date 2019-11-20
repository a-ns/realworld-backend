package com.example.adapters.persistence.post;

import org.springframework.data.jpa.repository.JpaRepository;

interface PostRepository extends JpaRepository<PostJpaEntity, Integer> {}
