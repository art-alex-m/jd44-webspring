package ru.netology.httpserver2;

public class Main {
    public static void main(String[] args) {
        Handlers.apply(new Server(ServerConfiguration.builder()
                .setPort(9999)
                .setBacklog(50)
                .setPoolSize(64)
                .build()
        )).listen();
    }
}
