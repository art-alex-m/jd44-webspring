package ru.netology.httpserver2.http;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequestParser {
    public static HttpRequest parse(BufferedReader in) throws IOException {
        /// read request line
        HttpRequestBuilder requestBuilder = HttpRequest.builder();
        String[] parts = in.readLine().split(" ");
        requestBuilder
                .setMethod(parts[0])
                .setUri(parts[1])
                .setVersion(parts[2]);

        /// read request headers
        while (true) {
            String header = in.readLine();
            if ("".equals(header)) break;
            String[] chunks = header.split(":\s+");
            try {
                requestBuilder.addHeader(chunks[0], chunks[1]);
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ignored) {
            }
        }
        return requestBuilder.build();
    }
}
