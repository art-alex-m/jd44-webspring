package ru.netology.httpserver2;

import java.net.URI;

public class Main {
    public static void main(String[] args) {
        new Server(ServerConfiguration.builder()
                .setPort(9999)
                .setBacklog(50)
                .setPoolSize(64)
                .build()
        ).listen();
    }
}
