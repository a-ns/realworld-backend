package com.example.adapters.web;

import com.example.adapters.web.dto.DraftArticle;
import com.example.adapters.web.dto.GetArticleResponse;
import com.example.application.domain.exceptions.ArticleAlreadyExistsException;
import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.Article;
import com.example.application.domain.model.PublishArticleCommand;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.DeleteArticleUseCase;
import com.example.application.domain.ports.in.FavoriteArticleUseCase;
import com.example.application.domain.ports.in.GetArticleQuery;
import com.example.application.domain.ports.in.PublishArticleUseCase;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/articles")
@AllArgsConstructor
public class ArticleController {

  private GetArticleQuery getArticleQuery;
  private PublishArticleUseCase publishArticleUseCase;
  private FavoriteArticleUseCase favoriteArticle;
  private DeleteArticleUseCase deleteArticleUseCase;

  @GetMapping
  public ResponseEntity<List<GetArticleResponse>> findArticles(
      @RequestParam List<String> tags,
      @RequestParam String author,
      @RequestParam Boolean favorited) {
    return ResponseEntity.ok(
        this.getArticleQuery
            .getRecentArticles(
                Optional.ofNullable(tags),
                Optional.ofNullable(author),
                Optional.ofNullable(favorited),
                Optional.empty())
            .stream()
            .map(
                article ->
                    GetArticleResponse.mapArticleToArticleResponse(article, article.getAuthor()))
            .collect(Collectors.toList()));
  }

  @GetMapping("/{slug}")
  public ResponseEntity<GetArticleResponse> findArticle(
      @AuthenticationPrincipal User user, @PathVariable String slug) {
    try {
      Article article =
          getArticleQuery.getArticle(slug, user == null ? Optional.empty() : Optional.of(user));
      return ResponseEntity.ok(
          GetArticleResponse.mapArticleToArticleResponse(article, article.getAuthor()));
    } catch (ArticleNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<GetArticleResponse> createArticle(
      @AuthenticationPrincipal User user, @RequestBody DraftArticle draftArticle) {
    try {
      DraftArticle.Body draft = draftArticle.getArticle();
      Article article =
          publishArticleUseCase.publishArticle(
              PublishArticleCommand.builder()
                  .body(draft.getBody())
                  .description(draft.getDescription())
                  .publisher(user)
                  .tagList(draft.getTagList())
                  .title(draft.getTitle())
                  .build());
      return ResponseEntity.ok(
          GetArticleResponse.mapArticleToArticleResponse(article, article.getAuthor()));
    } catch (ArticleAlreadyExistsException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/{slug}")
  public ResponseEntity<Void> deleteArticle(
      @AuthenticationPrincipal User user, @PathVariable String slug) {
    try {
      deleteArticleUseCase.deleteArticleBySlug(slug);
      return ResponseEntity.ok().build();
    } catch (ArticleNotFoundException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/{slug}/favorite")
  public ResponseEntity<GetArticleResponse> unfavoriteArticle(
      @AuthenticationPrincipal User user, @PathVariable String slug) {
    try {
      Article article = favoriteArticle.unfavoriteArticle(slug, user);
      return ResponseEntity.ok(
          GetArticleResponse.mapArticleToArticleResponse(article, article.getAuthor()));
    } catch (ArticleNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/{slug}/favorite")
  public ResponseEntity<GetArticleResponse> favoriteArticle(
      @AuthenticationPrincipal User user, @PathVariable String slug) {
    try {
      Article article = favoriteArticle.favoriteArticle(slug, user);
      return ResponseEntity.ok(
          GetArticleResponse.mapArticleToArticleResponse(article, article.getAuthor()));
    } catch (ArticleNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
