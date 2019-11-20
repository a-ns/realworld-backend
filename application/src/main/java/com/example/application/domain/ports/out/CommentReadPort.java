package com.example.application.domain.ports.out;

import com.example.application.domain.model.Comment;
import java.util.List;

public interface CommentReadPort {
  Comment getComment(Integer id);

  List<Comment> getComments();
}
