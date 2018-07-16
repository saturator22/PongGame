package com.codecool.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class TestHandler implements HttpHandler {
    final String GET_METHOD = "GET";
    final String POST_METHOD = "POST";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();

        if (method.equals(GET_METHOD) {
            
        }
    }
}
