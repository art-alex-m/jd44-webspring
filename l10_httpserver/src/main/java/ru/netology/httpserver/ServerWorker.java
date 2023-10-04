package ru.netology.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ServerWorker implements Runnable {
    private final int port;
    private final int backlog;
    private final ExecutorService executorService;

    public ServerWorker(int port, int backlog, ExecutorService executorService) {
        this.port = port;
        this.backlog = backlog;
        this.executorService = executorService;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port, backlog)) {
            System.out.println("Start accept connections on port " + port);
            while (!Thread.interrupted()) {
                Socket socket = serverSocket.accept();
                executorService.submit(new HandlerTask(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
