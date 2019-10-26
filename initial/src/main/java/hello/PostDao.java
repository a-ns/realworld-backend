package hello;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PostDao {
  private List<Post> posts = new ArrayList<>();
  private static Integer ID_COUNTER = 0;

  public List<Post> getPosts() {
    return this.posts;
  }

  public void addPost() {
    String id = String.valueOf(ID_COUNTER++);
    this.posts.add(
        Post.builder()
            .title(("New Title " + id))
            .author("Author" + id)
            .text("Body of " + id)
            .id(ID_COUNTER - 1)
            .build());
  }
}
