package ru.netology.servlets.repository;

import ru.netology.servlets.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<Post> all();

    Optional<Post> getById(long id);

    Post store(Post post);

    Optional<Post> update(Post post);

    Optional<Post> removeById(long id);
}
