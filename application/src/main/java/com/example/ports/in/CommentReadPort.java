package com.example.ports.in;

import com.example.Comment;
import java.util.List;

public interface CommentReadPort {
  Comment getComment(Integer id);

  List<Comment> getComments();
}
