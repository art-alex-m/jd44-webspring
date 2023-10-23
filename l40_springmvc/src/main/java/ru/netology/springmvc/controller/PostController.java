package ru.netology.springmvc.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.springmvc.model.Post;
import ru.netology.springmvc.service.PostService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> read() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Post read(@PathVariable long id) {
        return service.getById(id);
    }

    @PostMapping
    public Post create(@RequestBody Post post) {
        return service.store(post);
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable long id, @RequestBody Post post) {
        post.setId(id);
        return service.update(post);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.removeById(id);
    }
}
