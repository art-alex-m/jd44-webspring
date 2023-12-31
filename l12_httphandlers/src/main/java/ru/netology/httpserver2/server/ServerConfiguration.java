package ru.netology.httpserver2.server;

public record ServerConfiguration(int port, int poolSize, int backlog) {

    public static ServerConfigurationBuilder builder() {
        return new ServerConfigurationBuilder();
    }

    public int sleep() {
        return 200;
    }
}
