package com.codecool.Handlers;

import com.codecool.Model.TextInput;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class TestHandler implements HttpHandler {
    final String GET_METHOD = "GET";
    final String POST_METHOD = "POST";
    private String textResponse = "LOADING";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();

        if (method.equals(GET_METHOD)) {
            sendResponse(httpExchange, textResponse);
        } else if (method.equals(POST_METHOD)) {
            TextInput input = readAndParseJSON(httpExchange);

            textResponse = input.toString();
            sendResponse(httpExchange, textResponse);
        }
    }

    public TextInput readAndParseJSON(HttpExchange exchange) {
        try {
            String jsonString = readExchangeContent(exchange);
            Gson gson = new Gson();
            TextInput parsedJSON = gson.fromJson(jsonString, TextInput.class);
            return parsedJSON;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String readExchangeContent(HttpExchange exchange) {
        String jsonString = "";
        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            jsonString = br.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }
        return jsonString;
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
