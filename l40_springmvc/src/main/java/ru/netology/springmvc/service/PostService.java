package ru.netology.springmvc.service;

import org.springframework.stereotype.Service;
import ru.netology.springmvc.exception.NotFoundException;
import ru.netology.springmvc.model.Post;
import ru.netology.springmvc.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post store(Post post) {
        return repository.store(post);
    }

    public Post update(Post post) {
        return repository.update(post).orElseThrow(NotFoundException::new);
    }

    public void removeById(long id) {
        repository.removeById(id).orElseThrow(NotFoundException::new);
    }
}

