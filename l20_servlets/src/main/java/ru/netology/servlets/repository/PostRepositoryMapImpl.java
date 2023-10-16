package ru.netology.servlets.repository;

import ru.netology.servlets.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepositoryMapImpl implements PostRepository {

    private final AtomicLong id = new AtomicLong();
    private final Map<Long, Post> storage = new ConcurrentHashMap<>();

    public List<Post> all() {
        return storage.values().stream().toList();
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Post store(Post post) {
        long currentId = id.incrementAndGet();
        post.setId(currentId);
        storage.put(currentId, post);
        return post;
    }

    public Optional<Post> update(Post post) {
        storage.computeIfPresent(post.getId(), (k, c) -> post);
        return getById(post.getId());
    }

    public Optional<Post> removeById(long id) {
        return Optional.ofNullable(storage.remove(id));
    }
}
