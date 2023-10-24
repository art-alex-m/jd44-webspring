package ru.netology.springmvc.service;

import ru.netology.springmvc.model.Post;

import java.util.List;

public class PostServiceDtoMutator<D> implements PostService<D> {

    private final PostService<Post> service;

    private final DtoMutator<Post, D> mutator;

    public PostServiceDtoMutator(PostService<Post> service, DtoMutator<Post, D> mutator) {
        this.service = service;
        this.mutator = mutator;
    }

    @Override
    public List<D> all() {
        return service.all().stream().map(mutator::to).toList();
    }

    @Override
    public D getById(long id) {
        return mutator.to(service.getById(id));
    }

    @Override
    public D store(D post) {
        return mutator.to(service.store(mutator.from(post)));
    }

    @Override
    public D update(D post) {
        return mutator.to(service.update(mutator.from(post)));
    }

    @Override
    public void removeById(long id) {
        service.removeById(id);
    }
}
