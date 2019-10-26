package hello;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Query  implements GraphQLQueryResolver {

    @Autowired
    PostDao postDao;

     public List<Post> getRecentPosts(Integer count, Integer offset) {

        return postDao.getPosts();
    }

}

//
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//class Author {
//    private int id;
//    private String name;
//    private String thumbnail;
//    private List<Post> posts;
//}