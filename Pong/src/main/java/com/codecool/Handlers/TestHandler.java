package com.codecool.Handlers;

import com.codecool.Model.Ball;
import com.codecool.Model.GameRoom;
import com.codecool.Model.Player;
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
    private GameRoom gameRoom;
    private String textResponse;

    public TestHandler(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    @Override
    public void handle(HttpExchange httpExchange) {

        String method = httpExchange.getRequestMethod();

        if (method.equals(GET_METHOD)) {
            updateGameroom();
            updateScore();
            sendResponse(httpExchange, gameRoom.toJSON());
        } else if (method.equals(POST_METHOD)) {
            TextInput input = readAndParseJSON(httpExchange);
            gameRoom.getFirstPlayer().changePosition(input.toString());
            gameRoom.getSecondPlayer().changePosition(input.toString());
            textResponse = "";
            sendResponse(httpExchange, textResponse);
        }
    }

    public void updateScore() {
        float player1BoardEdge = 0;
        float player2BoardEdge = 800;

        float xPos = gameRoom.getBall().getxPos();

        if (xPos >= player1BoardEdge) {
            gameRoom.getSecondPlayer().addPoint();
            gameRoom.getBall().reset();

        } else if (xPos >= player2BoardEdge) {
            gameRoom.getFirstPlayer().addPoint();
            gameRoom.getBall().reset();
        }
    }



    public void updateGameroom() {
        updateBall(gameRoom.getBall());

    }

    public void updateBall(Ball ball) {
        ball.updateAngleIfOnBoardEdge();
        ball.updateAngleIfCollision(gameRoom.getFirstPlayer());
        ball.updateAngleIfCollision(gameRoom.getSecondPlayer());
        ball.updatePosition();
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
