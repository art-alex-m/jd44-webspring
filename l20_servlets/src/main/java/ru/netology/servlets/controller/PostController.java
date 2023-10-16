package ru.netology.servlets.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import ru.netology.servlets.model.Post;
import ru.netology.servlets.service.PostService;

import java.io.IOException;
import java.io.Reader;

public class PostController {
    private final PostService service;
    private final Gson gson;

    public PostController(PostService service) {
        this.service = service;
        this.gson = new Gson();
    }

    public void read(HttpServletResponse response) throws IOException {
        final var data = service.all();
        response.getWriter().print(gson.toJson(data));
    }

    public void read(long id, HttpServletResponse response) throws IOException {
        response.getWriter().print(gson.toJson(service.getById(id)));
    }

    public void create(Reader body, HttpServletResponse response) throws IOException {
        final Post post = gson.fromJson(body, Post.class);
        final Post data = service.store(post);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().print(gson.toJson(data));
    }

    public void update(Reader body, HttpServletResponse response) throws IOException {
        final Post post = gson.fromJson(body, Post.class);
        final Post data = service.update(post);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(gson.toJson(data));
    }

    public void delete(long id, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        service.removeById(id);
    }
}
