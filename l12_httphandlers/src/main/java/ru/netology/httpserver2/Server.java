package ru.netology.httpserver2;

import ru.netology.httpserver2.handler.Handler;
import ru.netology.httpserver2.handler.HandlerKey;
import ru.netology.httpserver2.http.HttpMethod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server {

    private final ServerConfiguration config;
    private final Map<HandlerKey, Handler> handlers = new ConcurrentHashMap<>();


    public Server(ServerConfiguration config) {
        this.config = config;
    }

    public void listen() {
        ExecutorService executorService = Executors.newWorkStealingPool(config.poolSize() + 1);

        executorService.submit(new ServerWorker(config.port(), config.backlog(), executorService, handlers));

        ForkJoinPool poolExecutor = (ForkJoinPool) executorService;

        while (!Thread.interrupted() && poolExecutor.getActiveThreadCount() > 0) {
            try {
                Thread.sleep(config.sleep());
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Server addHandler(HttpMethod method, String path, Handler handler) {
        addHandler(new HandlerKey(method, path), handler);
        return this;
    }

    public Server addHandler(HandlerKey key, Handler handler) {
        handlers.put(key, handler);
        return this;
    }
}
