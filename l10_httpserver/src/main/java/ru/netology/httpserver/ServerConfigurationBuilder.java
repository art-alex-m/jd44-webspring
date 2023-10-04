package ru.netology.httpserver;

public class ServerConfigurationBuilder {
    private int port;
    private int poolSize;
    private int backlog;

    public ServerConfigurationBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public ServerConfigurationBuilder setPoolSize(int poolSize) {
        this.poolSize = poolSize;
        return this;
    }

    public ServerConfigurationBuilder setBacklog(int backlog) {
        this.backlog = backlog;
        return this;
    }

    public ServerConfiguration build() {
        if (port <= 0) {
            throw new IllegalArgumentException("Port should be set");
        }
        if (backlog <= 0) {
            throw new IllegalArgumentException("Backlog should be set");
        }
        if (poolSize <= 0) {
            throw new IllegalArgumentException("PoolSize should be set");
        }

        return new ServerConfiguration(port, poolSize, backlog);
    }
}
