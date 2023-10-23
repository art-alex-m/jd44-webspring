package ru.netology.springmvc.service;

import ru.netology.springmvc.exception.NotFoundException;
import ru.netology.springmvc.model.Post;
import ru.netology.springmvc.repository.PostRepository;

import java.util.List;

public class PostServiceImpl implements PostService<Post> {
    private final PostRepository repository;

    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Post> all() {
        return repository.all().stream()
                .filter(Post::isNotRemoved)
                .toList();
    }

    @Override
    public Post getById(long id) {
        return repository.getById(id)
                .filter(Post::isNotRemoved)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Post store(Post post) {
        return repository.store(post);
    }

    @Override
    public Post update(Post post) {
        getById(post.getId());
        return repository.update(post).orElseThrow(NotFoundException::new);
    }

    @Override
    public void removeById(long id) {
        Post post = getById(id);
        post.setRemoved(true);
        repository.update(post).orElseThrow(NotFoundException::new);
    }
}

