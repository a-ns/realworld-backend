package hello;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostDao {
    private List<Post> posts = new ArrayList<>();
    private static Integer ID_COUNTER = 0;
    public List<Post> getPosts(){
        return this.posts;
    }

    public void addPost() {
        String id = String.valueOf(ID_COUNTER++);
        this.posts.add(Post.builder().title(("New Title " + id)).author("Author" + id).text("Body of " + id).build());
    }

}
