package com.example.adapter.persistence.post;

import com.example.Post;
import com.example.ports.in.AddPostPort;
import com.example.ports.in.UpdatePostPort;
import com.example.ports.out.GetPostsPort;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class PostPersistenceAdapter implements AddPostPort, GetPostsPort, UpdatePostPort {

  private final PostRepository repository;
  private final PostMapper mapper;

  public List<Post> getPosts() {
    return this.repository.findAll().stream()
        .map(mapper::mapToDomainEntity)
        .collect(Collectors.toList());
  }

  @Override
  public Post getPost(Integer id) {
    return mapper.mapToDomainEntity(
        this.repository.findById(id).orElseThrow(EntityNotFoundException::new));
  }

  public Post addPost(String title, String text, String author, String category) {

    PostJpaEntity added =
        PostJpaEntity.builder().title((title)).author(author).text(text).category(category).build();

    return mapper.mapToDomainEntity(this.repository.save(added));
  }

  @Override
  public Post updatePost(Integer id, Post post) {
    this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    return mapper.mapToDomainEntity(this.repository.save(mapper.mapToJpaEntity(post).withId(id)));
  }
}
