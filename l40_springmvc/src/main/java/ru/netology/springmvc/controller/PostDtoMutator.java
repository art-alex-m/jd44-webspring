package ru.netology.springmvc.controller;

import org.springframework.stereotype.Component;
import ru.netology.springmvc.dto.PostDto;
import ru.netology.springmvc.model.Post;
import ru.netology.springmvc.service.DtoMutator;

@Component
public class PostDtoMutator implements DtoMutator<Post, PostDto> {
    @Override
    public PostDto to(Post source) {
        return new PostDto(source.getId(), source.getContent());
    }

    @Override
    public Post from(PostDto dto) {
        return new Post(dto.id(), dto.content());
    }
}
