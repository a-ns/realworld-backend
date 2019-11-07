package com.example.ports.out;

import com.example.Comment;
import java.util.List;

public interface CommentReadPort {
  Comment getComment(Integer id);

  List<Comment> getComments();
}
