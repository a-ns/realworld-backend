package com.example.application.domain.ports.in;

import com.example.application.domain.model.Comment;
import com.example.application.domain.model.User;
import java.util.Collection;

public interface GetArticleCommentsQuery {

  Collection<Comment> getComments(String slug);

  Collection<Comment> getComments(String slug, User requester);
}
