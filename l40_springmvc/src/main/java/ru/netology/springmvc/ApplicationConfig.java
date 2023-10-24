package ru.netology.springmvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.netology.springmvc.dto.PostDto;
import ru.netology.springmvc.model.Post;
import ru.netology.springmvc.repository.PostRepository;
import ru.netology.springmvc.service.DtoMutator;
import ru.netology.springmvc.service.PostService;
import ru.netology.springmvc.service.PostServiceDtoMutator;
import ru.netology.springmvc.service.PostServiceImpl;

import java.util.List;

@Configuration
@EnableWebMvc
public class ApplicationConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new GsonHttpMessageConverter());
    }

    @Bean
    public PostService<PostDto> postService(DtoMutator<Post, PostDto> mutator, PostRepository postRepository) {
        return new PostServiceDtoMutator<>(new PostServiceImpl(postRepository), mutator);
    }
}
