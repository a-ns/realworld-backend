package com.example.application.domain.services.comment;

import com.example.application.domain.model.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Component
class CommentService {

  //  private CommentReadPort commentReadPort;

  Comment getComment(Integer id) {
    return null; //  this.commentReadPort.getComment(id);
  }

  List<Comment> getComments() {
    return Collections.emptyList(); // return  this.commentReadPort.getComments();
  }
}
