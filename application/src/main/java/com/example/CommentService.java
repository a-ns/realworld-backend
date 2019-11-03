package com.example;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CommentService {

  //  private CommentReadPort commentReadPort;

  Comment getComment(Integer id) {
    return null; //  this.commentReadPort.getComment(id);
  }

  List<Comment> getComments() {
    return Collections.emptyList(); // return  this.commentReadPort.getComments();
  }
}
