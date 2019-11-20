package com.example.adapters.web;

import com.example.application.domain.model.Post;
import com.example.application.domain.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Posts Mutations")
public class PostsMutationResolverTest {

  @Mock PostService postDao;

  @InjectMocks PostsMutationResolver sut;

  @Test
  @DisplayName("Adds a post")
  public void test() {
    // Arrange
    PostInput input = PostInput.builder().author("").category("").text("").title("").build();
    Post post = Post.builder().author("").category("").text("").title("").build();
    Mockito.when(postDao.createPost(Mockito.any(Post.class))).thenReturn(post);
    // Act
    Post actual = sut.addPost(input);
    // Assert
    Post expected = Post.builder().author("").category("").text("").title("").build();
    Assertions.assertEquals(expected.getAuthor(), actual.getAuthor());
    Assertions.assertEquals(expected.getCategory(), actual.getCategory());
    Assertions.assertEquals(expected.getText(), actual.getText());
    Assertions.assertEquals(expected.getTitle(), actual.getTitle());
  }
}
