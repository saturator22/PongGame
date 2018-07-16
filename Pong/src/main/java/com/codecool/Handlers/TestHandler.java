package com.codecool.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class TestHandler implements HttpHandler {
    final String GET_METHOD = "GET";
    final String POST_METHOD = "POST";
    private String textResponse = "loading";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();

        if (method.equals(GET_METHOD) {
            sendResponse(httpExchange, textResponse);
        } else if (method.equals(POST_METHOD)) {
            sendResponse(httpExchange, textResponse);
        }
    }

    public void sendResponse(HttpExchange exchange, String response) {
        byte[] bytes = response.getBytes();
        try {
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
