package ru.netology.servlets.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.servlets.AppConfig;
import ru.netology.servlets.controller.PostController;
import ru.netology.servlets.exception.NotFoundException;

import java.io.IOException;

public class PostServlet extends HttpServlet {
    public static final String APPLICATION_JSON = "application/json";

    private PostController controller;

    @Override
    public void init() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        controller = applicationContext.getBean(PostController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final var path = req.getRequestURI();
        if (!path.matches("/api/posts/\\d+")) {
            controller.read(resp);
            return;
        }
        controller.read(getId(req), resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        controller.create(req.getReader(), resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        getId(req);
        controller.update(req.getReader(), resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        controller.delete(getId(req), resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType(APPLICATION_JSON);
        try {
            super.service(req, resp);
        } catch (NotFoundException ex) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private Long getId(HttpServletRequest req) {
        final String path = req.getRequestURI();
        try {
            String number = path.substring(path.lastIndexOf("/") + 1);
            return Long.parseLong(number);
        } catch (NumberFormatException ignored) {
        }

        throw new NotFoundException();
    }
}

