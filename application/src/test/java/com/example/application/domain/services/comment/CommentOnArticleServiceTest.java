package com.example.application.domain.services.comment;

import com.example.application.domain.model.Article;
import com.example.application.domain.model.Comment;
import com.example.application.domain.model.Profile;
import com.example.application.domain.model.User;
import com.example.application.domain.ports.in.CommentOnArticleUseCase;
import com.example.application.domain.ports.in.GetProfileQuery;
import com.example.application.domain.ports.out.LoadArticlePort;
import com.example.application.domain.ports.out.LoadProfilePort;
import com.example.application.domain.ports.out.SaveCommentPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentOnArticleServiceTest {
    @InjectMocks
    private CommentOnArticleService sut;
    @Mock
    private SaveCommentPort saveCommentPort;
    @Mock
    private GetProfileQuery loadProfileQuery;
    @Mock
    private LoadArticlePort loadArticlePort;

    @Test
    @DisplayName("a user can comment on an article")
    void a_user_can_comment_on_an_article(){
        // Arrange
        String slug = "article-slug";
        String body = "comment body";
        Integer articleId = 1234;
        String author = "author";
        CommentOnArticleUseCase.PublishCommentCommand input = CommentOnArticleUseCase.PublishCommentCommand.builder().articleSlug(slug).body(body).commentAuthor(User.builder().username(author).build()).build();

        Mockito.when(loadProfileQuery.getProfile(author, Optional.empty())).thenReturn(Profile.builder().username(author).build());
        Mockito.when(loadArticlePort.findArticle(slug)).thenReturn(Optional.of(Article.builder().id(articleId).slug(slug).build()));
        Mockito.when(saveCommentPort.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
        // Act
        Comment actual = sut.publishComment(input);
        // Assert
        Assertions.assertEquals(author, actual.getAuthor().getUsername());
        Assertions.assertEquals(body, actual.getBody());
        Assertions.assertFalse(actual.getId().isEmpty());
    }
}