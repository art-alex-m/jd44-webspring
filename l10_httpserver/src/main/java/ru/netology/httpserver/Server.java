package ru.netology.httpserver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server {

    private final ServerConfiguration config;


    public Server(ServerConfiguration config) {
        this.config = config;
    }

    public void listen() {
        ExecutorService executorService = Executors.newWorkStealingPool(config.poolSize() + 1);

        executorService.submit(new ServerWorker(config.port(), config.backlog(), executorService));

        ForkJoinPool poolExecutor = (ForkJoinPool) executorService;

        while (!Thread.interrupted() && poolExecutor.getActiveThreadCount() > 0) {
            try {
                Thread.sleep(config.sleep());
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
