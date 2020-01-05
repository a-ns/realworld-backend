package com.example.adapters.persistence.article;

import org.springframework.data.jpa.repository.JpaRepository;

interface ArticleFavoriteRepository extends JpaRepository<ArticleFavoriteJpaEntity, Integer> {

  Boolean existsByArticleIdAndUserId(Integer articleId, Integer userId);

  Integer countByArticleId(Integer articleId);

  ArticleFavoriteJpaEntity findByArticleIdAndUserId(Integer articleId, Integer userId);
}
