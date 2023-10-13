package ru.netology.httpserver2;

import ru.netology.httpserver2.server.Server;
import ru.netology.httpserver2.server.ServerConfiguration;

import java.util.Arrays;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int port = Optional.ofNullable(args).stream()
                .flatMap(Arrays::stream)
                .findFirst()
                .map(Integer::parseInt)
                .orElse(9999);

        Server server = new Server(ServerConfiguration.builder()
                .setPort(port)
                .setBacklog(50)
                .setPoolSize(64)
                .build());

        Handlers.apply(server).listen();

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        Thread.currentThread().join();
    }
}
