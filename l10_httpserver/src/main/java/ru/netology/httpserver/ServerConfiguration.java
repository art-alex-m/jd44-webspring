package ru.netology.httpserver;

public record ServerConfiguration(int port, int poolSize, int backlog) {

    public static ServerConfigurationBuilder builder() {
        return new ServerConfigurationBuilder();
    }

    public int sleep() {
        return 200;
    }
}
