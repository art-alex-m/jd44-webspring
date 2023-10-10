package ru.netology.httpserver2.http;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class HttpRequestParser {
    private static final int BUFFER_SIZE = 512;

    public static HttpRequest parse(BufferedInputStream inputStream) throws IOException, FileUploadException {
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
        if (request.isMultipartFormData()) {
            File repository = new File("uploads");
            FileItemFactory factory = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository);
            FileUpload upload = new FileUpload(factory);
            RequestContext context = new HttpRequestContext(request.getHeader(HttpHeader.CONTENT_TYPE), inputStream);
            List<FileItem> fileItems = upload.parseRequest(context);
            requestBuilder.setPostParams(fileItems).setFiles(fileItems);
        }

        return requestBuilder.build();
    }

    public static String readLine(BufferedInputStream inputStream) throws IOException {
        int bytesToRead = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int delimiterIndex = -1;

        do {
            inputStream.mark(bytesToRead + buffer.length);
            inputStream.skip(bytesToRead);
            int bytesRead = inputStream.read(buffer);
            delimiterIndex = bytesRead < 0 ? 0 : indexOfDelimiter(buffer, Http.DELIMITER_BYTES);
            bytesToRead += Math.abs(delimiterIndex);
            inputStream.reset();
        } while (delimiterIndex < 0);

        String line = new String(inputStream.readNBytes(bytesToRead));
        inputStream.skip(Http.DELIMITER_BYTES.length);

        return line;
    }

    public static int indexOfDelimiter(byte[] buffer, byte[] delimiter) {
        int lastIndex = buffer.length;
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] != delimiter[0]) {
                continue;
            }
            lastIndex = i;
            boolean found = true;
            int j = 1;
            for (i++; j < delimiter.length; j++, i++) {
                if (i >= buffer.length || buffer[i] != delimiter[j]) {
                    found = false;
                    if (i < buffer.length) {
                        lastIndex = buffer.length;
                    }
                    break;
                }
            }
            if (found) {
                return lastIndex;
            }
        }

        return -lastIndex;
    }
}
