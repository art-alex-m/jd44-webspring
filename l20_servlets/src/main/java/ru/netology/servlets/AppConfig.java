package ru.netology.servlets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netology.servlets.controller.PostController;
import ru.netology.servlets.repository.PostRepository;
import ru.netology.servlets.repository.PostRepositoryMapImpl;
import ru.netology.servlets.service.PostService;

@Configuration
public class AppConfig {

    @Bean
    public PostRepository postRepository() {
        return new PostRepositoryMapImpl();
    }

    @Bean
    public PostService postService(PostRepository postRepository) {
        return new PostService(postRepository);
    }

    @Bean
    public PostController postController(PostService postService) {
        return new PostController(postService);
    }
}
