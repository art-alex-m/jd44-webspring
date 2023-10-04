package ru.netology.httpserver2;

import ru.netology.httpserver2.http.HttpHeader;
import ru.netology.httpserver2.http.HttpResponseBuilder;
import ru.netology.httpserver2.http.HttpStatus;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class HandlerTask implements Runnable {

    private final static List<String> VALID_PATHS = List.of("/index.html", "/spring.svg", "/spring.png",
            "/resources.html", "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html",
            "/events.html", "/events.js");

    private final Socket socket;

    public HandlerTask(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream())
        ) {
            // read only request line for simplicity
            // must be in form GET /path HTTP/1.1
            String requestLine = in.readLine();
            String[] parts = requestLine.split(" ");

            if (parts.length != 3) {
                out.write(HttpResponseBuilder.badRequest().getBytes());
                out.flush();
                socket.close();
                return;
            }
            System.out.println("Accept new request " + Arrays.toString(parts));
            final var path = parts[1];
            if (!VALID_PATHS.contains(path)) {
                out.write(HttpResponseBuilder.notFound().getBytes());
                out.flush();
                return;
            }

            Path filePath = Path.of(".", "public", path);
            String mimeType = Files.probeContentType(filePath);

            // special case for classic
            if (path.equals("/classic.html")) {
                String template = Files.readString(filePath);
                byte[] content = template
                        .replace("{time}", LocalDateTime.now().toString())
                        .getBytes();

                out.write(HttpResponseBuilder.builder()
                        .setStatus(HttpStatus.OK)
                        .addHeader(HttpHeader.CONTENT_LENGTH, content.length)
                        .addHeader(HttpHeader.CONNECTION, "close")
                        .addHeader(HttpHeader.CONTENT_TYPE, mimeType)
                        .getBytes());
                out.write(content);
                out.flush();
                return;
            }

            long length = Files.size(filePath);
            String response = HttpResponseBuilder.builder()
                    .setStatus(HttpStatus.OK)
                    .addHeader(HttpHeader.CONTENT_TYPE, mimeType)
                    .addHeader(HttpHeader.CONNECTION, "close")
                    .addHeader(HttpHeader.CONTENT_LENGTH, length)
                    .toString();

            out.write(response.getBytes());
            Files.copy(filePath, out);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
