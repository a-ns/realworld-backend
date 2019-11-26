package com.example.application.domain.services;

import com.example.application.domain.CreatePostUseCase;
import com.example.application.domain.model.Post;
import com.example.application.domain.ports.in.AddPostPort;
import com.example.application.domain.ports.in.UpdatePostPort;
import com.example.application.domain.ports.out.GetPostsPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Component
@Transactional
class PostService implements CreatePostUseCase, UpdatePostPort {

  private final UpdatePostPort updatePostPort;
  private final GetPostsPort getPostsPort;
  private final AddPostPort addPostsPort;

  public List<Post> getPosts() {
    return this.getPostsPort.getPosts();
  }

  public Post updatePost(Integer id, Post input) {
    try {
      Post existing = getPostsPort.getPost(id);

      // business validation logic
      if (input.getAuthor() != null) existing.setAuthor(input.getAuthor());
      if (input.getCategory() != null) existing.setCategory(input.getCategory());
      if (input.getText() != null) existing.setText(input.getText());
      if (input.getTitle() != null) existing.setTitle(input.getTitle());
      return this.updatePostPort.updatePost(id, existing);
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public Post createPost(Post input) {

    // do some business validation logic
    return this.addPostsPort.addPost(
            input.getTitle(), input.getText(), input.getAuthor(), input.getCategory());
  }
}
