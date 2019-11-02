package com.example;

import com.example.ports.in.GetPostsPort;
import com.example.ports.out.AddPostPort;
import com.example.ports.out.UpdatePostPort;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Transactional
public class PostService /* implements PostUseCases */ {

  private final UpdatePostPort updatePostPort;
  private final GetPostsPort getPostsPort;
  private final AddPostPort addPostsPort;

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

  public Post createPost(Post input) {
    return this.addPostsPort.addPost(
        input.getTitle(), input.getText(), input.getAuthor(), input.getCategory());
  }
}
