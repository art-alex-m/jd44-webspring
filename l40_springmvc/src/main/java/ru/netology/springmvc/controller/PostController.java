package ru.netology.springmvc.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.springmvc.dto.PostDto;
import ru.netology.springmvc.service.PostService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
public class PostController {
    private final PostService<PostDto> service;

    public PostController(PostService<PostDto> service) {
        this.service = service;
    }

    @GetMapping
    public List<PostDto> read() {
        return service.all();
    }

    @GetMapping("/{id}")
    public PostDto read(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public PostDto create(@RequestBody PostDto post) {
        return service.store(post);
    }

    @PutMapping("/{id}")
    public PostDto update(@PathVariable long id, @RequestBody PostDto post) {
        return service.update(new PostDto(id, post.content()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.removeById(id);
    }
}
