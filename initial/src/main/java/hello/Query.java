package hello;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

  @Autowired PostDao postDao;

  public List<Post> getRecentPosts(Integer count, Integer offset) {

    return postDao.getPosts();
  }
}
