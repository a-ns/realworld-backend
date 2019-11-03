package com.example.adapter.persistence.post;

import org.springframework.data.jpa.repository.JpaRepository;

interface PostRepository extends JpaRepository<PostJpaEntity, Integer> {}
