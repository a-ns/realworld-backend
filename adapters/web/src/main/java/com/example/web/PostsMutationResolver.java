package com.example.web;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.ports.out.AddPostPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostsMutationResolver implements GraphQLMutationResolver {

  @Autowired private AddPostPort postDao;

  public Boolean addPost() {
    try {
      this.postDao.addPost("Title", "Text", "Bill", "science");
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
