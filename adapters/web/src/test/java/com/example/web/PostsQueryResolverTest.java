package com.example.web;

import com.example.Post;
import com.example.ports.out.GetPostsPort;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Posts query resolver")
public class PostsQueryResolverTest {

  @InjectMocks PostsQueryResolver sut;

  @Mock GetPostsPort postsDao;

  @Test
  @DisplayName("Gets posts")
  public void test_get_posts() {

    // Arrange
    Mockito.when(this.postsDao.getPosts()).thenReturn(new ArrayList<>());
    // Act
    List<Post> actual = this.sut.getRecentPosts(0, 0);
    // Assert
    Assertions.assertEquals(0, actual.size());
  }
}
