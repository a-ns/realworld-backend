package com.example.adapters.event;

import com.example.application.domain.PostService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PostCreateListener implements Listener<PostEvent> {

  private final PostService service;

  private final PostEventMapper mapper;

  private final Logger logger;

  @Override
  public String listensOn() {
    return "PostCreateCommand";
  }

  @Override
  public void process(PostEvent event) {
    logger.warn("In PostCreateListener");
    service.createPost(mapper.eventDataToDomain(event));
  }

  @Override
  public Class<PostEvent> type() {
    return PostEvent.class;
  }
}
