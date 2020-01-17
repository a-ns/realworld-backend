package com.example.application.domain.ports.in;

import com.example.application.domain.model.Comment;
import java.util.Collection;

public interface GetArticleCommentsQuery {

  Collection<Comment> getComments(String slug);
}
