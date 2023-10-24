package ru.netology.springmvc.service;

import java.util.List;

public interface PostService<P> {
    List<P> all();

    P getById(long id);

    P store(P post);

    P update(P post);

    void removeById(long id);
}
