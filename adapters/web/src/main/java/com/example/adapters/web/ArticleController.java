package com.example.adapters.web;

import com.example.adapters.web.dto.input.CreateArticlePayload;
import com.example.adapters.web.dto.input.CreateCommentPayload;
import com.example.adapters.web.dto.output.GetArticleResponse;
import com.example.adapters.web.dto.output.GetCommentResponse;
import com.example.application.domain.exceptions.ArticleAlreadyExistsException;
import com.example.application.domain.exceptions.ArticleNotFoundException;
import com.example.application.domain.model.Article;
import com.example.application.domain.model.Comment;
import com.example.application.domain.model.CommentId;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.*;
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
  private CommentOnArticleUseCase commentOnArticleUseCase;
  private GetArticleCommentsQuery getArticleCommentsQuery;
  private DeleteCommentOnArticleUseCase deleteCommentOnArticleUseCase;

  @PostMapping("/{slug}/comments")
  public ResponseEntity<GetCommentResponse> createComment(
      @RequestBody CreateCommentPayload payload,
      @AuthenticationPrincipal User user,
      @PathVariable String slug) {
    try {
      Comment created =
          this.commentOnArticleUseCase.publishComment(
              CommentOnArticleUseCase.PublishCommentCommand.builder()
                  .commentAuthor(user)
                  .articleSlug(slug)
                  .body(payload.getComment().getBody())
                  .build());
      return ResponseEntity.ok(GetCommentResponse.from(created));
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{slug}/comments")
  public ResponseEntity<List<GetCommentResponse>> getComments(
      @AuthenticationPrincipal User user, @PathVariable String slug) {
    try {
      var comments = this.getArticleCommentsQuery.getComments(slug, user);
      return ResponseEntity.ok(
          comments.stream().map(GetCommentResponse::from).collect(Collectors.toList()));
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{slug}/comments/{id}")
  public ResponseEntity<?> deleteComment(
      @AuthenticationPrincipal User user, @PathVariable String slug, @PathVariable Integer id) {
    try {
      var input =
          DeleteCommentOnArticleUseCase.DeleteCommentCommand.builder()
              .requester(user)
              .slug(slug)
              .commentId(CommentId.builder().id(id).build())
              .build();
      this.deleteCommentOnArticleUseCase.delete(input);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<GetArticleResponse>> findArticles(
      @RequestParam String tags,
      @RequestParam String author,
      @RequestParam String favorited,
      @RequestParam Integer limit,
      @RequestParam Integer offset,
      @AuthenticationPrincipal User user) {
    return ResponseEntity.ok(
        this.getArticleQuery
            .getRecentArticles(
                Optional.of(tags),
                Optional.ofNullable(author),
                Optional.ofNullable(favorited),
                Optional.ofNullable(limit),
                Optional.ofNullable(offset),
                Optional.ofNullable(user))
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
      Article article = getArticleQuery.getArticle(slug, user);
      return ResponseEntity.ok(
          GetArticleResponse.mapArticleToArticleResponse(article, article.getAuthor()));
    } catch (ArticleNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping
  public ResponseEntity<GetArticleResponse> createArticle(
      @AuthenticationPrincipal User user, @RequestBody CreateArticlePayload createArticlePayload) {
    try {
      CreateArticlePayload.Body draft = createArticlePayload.getArticle();
      Article article =
          publishArticleUseCase.publishArticle(
              PublishArticleUseCase.PublishArticleCommand.builder()
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
