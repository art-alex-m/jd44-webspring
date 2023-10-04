package ru.netology.httpserver;

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
