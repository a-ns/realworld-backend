package com.example.adapters.persistence.article;

import com.example.adapters.persistence.user.UserJpaEntity;
import com.example.adapters.persistence.user.UserPersistenceMapper;
import com.example.adapters.persistence.user.UserRepository;
import com.example.application.domain.model.Article;
import com.example.application.domain.ports.out.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class LoadArticleArticlePersistenceAdapter
    implements LoadArticlePort,
        SaveArticlePort,
        UpdateArticlePort,
        DeleteArticlePort,
        LoadArticleFavoritedPort,
        LoadArticleFavoriteCountPort,
        LoadRecentArticlesPort,
        SaveFavoritePort {

  private ArticleRepository repository;
  private ArticleMapper mapper;
  private ArticleFavoriteRepository articleFavoriteRepository;
  private UserRepository userRepository;
  private UserPersistenceMapper userMapper;

  @Override
  public Article createArticle(Article article) {
    UserJpaEntity author = userRepository.findByUsername(article.getAuthor().getUsername());
    ArticleJpaEntity draft = mapper.mapDomainToJpa(article);
    draft.setAuthor(author);
    return mapper.mapJpaToDomain(repository.save(draft), 0);
  }

  @Override
  public Article deleteArticleById(Integer id) {
    ArticleJpaEntity entity = repository.findById(id).get();
    Article article = mapper.mapJpaToDomain(entity, articleFavoriteRepository.countByArticleId(id));
    repository.delete(entity);
    return article;
  }

  @Override
  public Article updateArticle(Article draft) {
    UserJpaEntity author = userRepository.findByUsername(draft.getAuthor().getUsername());
    ArticleJpaEntity mapped = mapper.mapDomainToJpa(draft);
    mapped.setAuthor(author);
    return mapper.mapJpaToDomain(
        repository.save(mapped), articleFavoriteRepository.countByArticleId(mapped.getId()));
  }

  @Override
  public Optional<Article> findArticle(String slug) {
    Optional<ArticleJpaEntity> article = repository.findBySlug(slug);
    if (article.isEmpty()) return Optional.empty();

    return Optional.of(
        mapper.mapJpaToDomain(
            article.get(), articleFavoriteRepository.countByArticleId(article.get().getId())));
  }

  @Override
  public Optional<Article> findArticleById(Integer id) {
    Optional<ArticleJpaEntity> article = repository.findById(id);
    if (article.isEmpty()) return Optional.empty();
    return Optional.of(
        mapper.mapJpaToDomain(article.get(), articleFavoriteRepository.countByArticleId(id)));
  }

  @Override
  public Boolean isArticleFavoritedBy(Integer articleId, Integer userId) {
    return articleFavoriteRepository.existsByArticleIdAndUserId(articleId, userId);
  }

  @Override
  public Integer getFavoriteCount(Integer articleId) {
    return articleFavoriteRepository.countByArticleId(articleId);
  }

  @Override
  public Article addFavorite(Integer articleId, Integer userId) {
    ArticleFavoriteJpaEntity favorite =
        this.articleFavoriteRepository.save(
            ArticleFavoriteJpaEntity.builder().articleId(articleId).userId(userId).build());
    return mapper.mapJpaToDomain(
        repository.findById(favorite.getArticleId()).get(),
        articleFavoriteRepository.countByArticleId(articleId));
  }

  @Override
  public Article removeFavorite(Integer articleId, Integer userId) {
    ArticleFavoriteJpaEntity favorite =
        this.articleFavoriteRepository.findByArticleIdAndUserId(articleId, userId);
    this.articleFavoriteRepository.delete(favorite);
    return mapper.mapJpaToDomain(
        this.repository.findById(favorite.getArticleId()).get(),
        articleFavoriteRepository.countByArticleId(articleId));
  }

  @Override
  public Collection<Article> loadRecentArticles(Optional<String> tags, Optional<String> author, Optional<String> favorited, Integer limit, Integer offset) {
    return Collections.emptyList();
  }
}
