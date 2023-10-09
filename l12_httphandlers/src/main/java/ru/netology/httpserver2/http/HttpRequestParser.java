package ru.netology.httpserver2.http;

import java.io.BufferedInputStream;
import java.io.IOException;

public class HttpRequestParser {
    private static final int BUFFER_SIZE = 512;

    public static HttpRequest parse(BufferedInputStream inputStream) throws IOException {
        /// read request line
        HttpRequestBuilder requestBuilder = HttpRequest.builder();
        String[] parts = readLine(inputStream).split(" ");
        requestBuilder.setMethod(parts[0]).setUri(parts[1]).setVersion(parts[2]);

        /// read request headers
        while (true) {
            String header = readLine(inputStream);
            if ("".equals(header)) break;
            String[] chunks = header.split(":\s+");
            try {
                requestBuilder.addHeader(chunks[0], chunks[1]);
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ignored) {
            }
        }

        /// check body
        HttpRequest request = requestBuilder.build();
        int contentLength = request.getHeader(HttpHeader.CONTENT_LENGTH, true);
        if (contentLength <= 0) {
            return request;
        }

        /// set post params
        if (request.isXWwwFormUrlencoded()) {
            byte[] body = inputStream.readNBytes(contentLength);
            requestBuilder.setPostParams(new String(body));
        }

        return requestBuilder.build();
    }

    public static String readLine(BufferedInputStream inputStream) throws IOException {
        int bytesToRead = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int delimiterIndex = -1;

        do {
            inputStream.mark(bytesToRead + buffer.length - 1);
            inputStream.skip(bytesToRead);
            int bytesRead = inputStream.read(buffer);
            delimiterIndex = bytesRead < 0 ? 0 : indexOfDelimiter(buffer, Http.DELIMITER_BYTES);
            bytesToRead += delimiterIndex >= 0 ? delimiterIndex : buffer.length - 1;
            inputStream.reset();
        } while (delimiterIndex < 0);

        String line = new String(inputStream.readNBytes(bytesToRead));
        inputStream.skip(Http.DELIMITER_BYTES.length);

        return line;
    }

    public static int indexOfDelimiter(byte[] buffer, byte[] delimiter) {
        for (int i = 0; i < buffer.length; i++) {
            boolean found = true;
            for (int j = 0; j < delimiter.length; j++) {
                if (buffer[i + j] != delimiter[j]) {
                    i += j;
                    found = false;
                    break;
                }
            }
            if (found) return i;
        }

        return -1;
    }
}
