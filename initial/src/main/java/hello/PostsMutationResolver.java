package hello;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostsMutationResolver implements GraphQLMutationResolver {

  @Autowired private PostDao postDao;

  public Boolean addPost() {
    try {
      this.postDao.addPost();
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
