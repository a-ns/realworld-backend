package com.example.adapter.persistence;

import com.example.Post;
import com.example.ports.in.GetPostsPort;
import com.example.ports.out.AddPostPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class PostPersistenceAdapter implements AddPostPort, GetPostsPort {

  private static Integer ID_COUNTER = 0;

  private final PostRepository repository;
  private final PostMapper mapper;

  public List<Post> getPosts() {
    return this.repository.findAll().stream()
        .map(mapper::mapToDomainEntity)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Post> getPost(Integer id) {
    return this.repository.findAll().stream()
        .filter(post -> post.getId() == id)
        .map(mapper::mapToDomainEntity)
        .findFirst();
  }

  public Post addPost(String title, String text, String author, String category) {

    PostJpaEntity added =
        PostJpaEntity.builder()
            .title((title))
            .author(author)
            .text(text)
            .category(category)
            .id(ID_COUNTER++)
            .build();

    return mapper.mapToDomainEntity(this.repository.save(added));
  }
}
