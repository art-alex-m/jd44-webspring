package ru.netology.httpserver2;

import ru.netology.httpserver2.handler.Handler;
import ru.netology.httpserver2.handler.HandlerKey;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ServerWorker implements Runnable {
    private final int port;
    private final int backlog;
    private final ExecutorService executorService;
    private final Map<HandlerKey, Handler> handlers;

    public ServerWorker(int port, int backlog, ExecutorService executorService, Map<HandlerKey, Handler> handlers) {
        this.port = port;
        this.backlog = backlog;
        this.executorService = executorService;
        this.handlers = handlers;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port, backlog)) {
            System.out.println("Start accept connections on port " + port);
            while (!Thread.interrupted()) {
                Socket socket = serverSocket.accept();
                executorService.submit(new HandlerTask(socket, handlers));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
