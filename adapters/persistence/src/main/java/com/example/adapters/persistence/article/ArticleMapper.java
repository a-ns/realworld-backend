package com.example.adapters.persistence.article;

import com.example.adapters.persistence.tag.TagJpaEntity;
import com.example.application.domain.model.Article;
import com.example.application.domain.model.Profile;
import java.time.ZoneId;
import java.util.Collections;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
class ArticleMapper {
  Article mapJpaToDomain(ArticleJpaEntity jpa, Integer favoritesCount) {
    return Article.builder()
        .title(jpa.getTitle())
        .tags(
            jpa.getTags() == null
                ? Collections.emptyList()
                : jpa.getTags().stream().map(tag -> tag.getTag()).collect(Collectors.toList()))
        .slug(jpa.getSlug())
        .description(jpa.getDescription())
        .body(jpa.getBody())
        .author(
            Profile.builder()
                .username(jpa.getAuthor().getUsername())
                .following(false)
                .image(jpa.getAuthor().getImage())
                .bio(jpa.getAuthor().getBio())
                .build())
        .id(jpa.getId())
        .favorited(false)
        .favoritesCount(favoritesCount)
        .createdAt(jpa.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
        .updatedAt(jpa.getUpdatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
        .build();
  }

  ArticleJpaEntity mapDomainToJpa(Article article) {

    return ArticleJpaEntity.builder()
        //                .author(UserJpaEntity.builder()
        //                        .id(author.getId())
        //                        .email(author.getEmail())
        //                        .bio(author.getBio())
        //                        .image(author.getImage())
        //                        .username(author.getUsername())
        //                        .password(author.getPassword())
        //                        .build())
        .body(article.getBody())
        .description(article.getDescription())
        .id(article.getId())
        .slug(article.getSlug())
        .title(article.getTitle())
        .tags(
            article.getTags() == null
                ? Collections.emptyList()
                : article.getTags().stream()
                    .map(str -> TagJpaEntity.builder().tag(str).build())
                    .collect(Collectors.toList()))
        .build();
  }
}
