package com.codecool.Handlers;

import com.codecool.Model.Ball;
import com.codecool.Model.GameRoom;
import com.codecool.Model.TextInput;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;

public class TestHandler implements HttpHandler {
    final String GET_METHOD = "GET";
    final String POST_METHOD = "POST";
    private static Map<String, GameRoom> gameRooms = new HashMap<>();

    @Override
    public void handle(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getResponseHeaders().getFirst("Cookie");
        HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
        String method = httpExchange.getRequestMethod();

        GameRoom gameRoom = gameRooms.get(cookie.getValue());

        if (method.equals(GET_METHOD)) {
            updateGameroom(gameRoom);
            sendResponse(httpExchange, gameRoom.toJSON());

        } else if (method.equals(POST_METHOD)) {
            String player = getPlayerFromCookie(cookie);
            TextInput input = readAndParseJSON(httpExchange);

            if(player.equalsIgnoreCase("p1")) {
                gameRoom.getFirstPlayer().changePosition(input.toString());

            } else if(player.equalsIgnoreCase("p2")) {
                gameRoom.getSecondPlayer().changePosition(input.toString());
            }

            String textResponse = "";
            sendResponse(httpExchange, textResponse);
        }
    }

    private String getPlayerFromCookie(HttpCookie cookie) {
        return cookie.getComment().split(":")[0];
    }

    public static Map<String, GameRoom> getGameRooms() {
        return gameRooms;
    }

    public static void addToGameRooms(String roomId, GameRoom gameRoom) {
        gameRooms.put(roomId, gameRoom);
    }

    public static void removeFromGameRooms(String roomId) {
        gameRooms.remove(roomId);
    }

    public void updateGameroom(GameRoom gameRoom) {
        Ball ball = gameRoom.getBall();
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
