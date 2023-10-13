package ru.netology.httpserver2.server;

import ru.netology.httpserver2.handler.Handler;
import ru.netology.httpserver2.handler.HandlerKey;
import ru.netology.httpserver2.http.HttpMethod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

    private final ServerConfiguration config;
    private final Map<HandlerKey, Handler> handlers = new ConcurrentHashMap<>();
    private ExecutorService executorService;


    public Server(ServerConfiguration config) {
        this.config = config;
    }

    public void listen() {
        executorService = Executors.newWorkStealingPool(config.poolSize() + 1);
        executorService.submit(new ServerWorker(config.port(), config.backlog(), executorService, handlers));
    }

    public void shutdown() {
        executorService.shutdown();
        while (true) {
            try {
                if (!executorService.awaitTermination(config.sleep(), TimeUnit.MILLISECONDS)) break;
            } catch (InterruptedException ignored) {
                break;
            }
        }
    }

    public Server addHandler(HttpMethod method, String path, Handler handler) {
        return addHandler(new HandlerKey(method, path), handler);
    }

    public Server addHandler(HandlerKey key, Handler handler) {
        handlers.put(key, handler);
        return this;
    }
}
