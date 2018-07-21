package com.codecool.Handlers;

import com.codecool.Helper.Redirector;
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
import java.util.List;
import java.util.Map;

public class TestHandler implements HttpHandler {
    final String GET_METHOD = "GET";
    final String POST_METHOD = "POST";
    private static Map<String, GameRoom> gameRooms = new HashMap<>();
    HttpCookie cookie;
    GameRoom gameRoom;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://192.168.10.193:8000/test");
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        String method = httpExchange.getRequestMethod();

        if(cookieStr == null) {
            gameRoom = null;
            cookie = null;
        } else {
            cookie = HttpCookie.parse(cookieStr).get(0);
            gameRoom = gameRooms.get(getRoomIdFromCookie(cookie));
        }

        if (method.equals(GET_METHOD)) {
            if (gameRoom != null) {
                updateGameroom(gameRoom);
                sendResponse(httpExchange, gameRoom.toJSON());
            } else {
                sendResponse(httpExchange, "{}");
            }

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

    public static void resetGameRoom(String roomId) {
        GameRoom gameRoom = gameRooms.get(roomId);
        gameRoom.getFirstPlayer().resetGamePlayStats();
        gameRoom.getSecondPlayer().resetGamePlayStats();
        Ball newBall = new Ball(400f, 240f, 0f, 10f, 0.02f);
        gameRoom.setBall(newBall);
    }

    private String getRoomIdFromCookie(HttpCookie cookie) {
        try {
            Map<String, String> parsedCookieValues = Redirector.parseFormData(cookie.toString());
            return parsedCookieValues.get("roomId");
        } catch (IOException e) {
            e.printStackTrace();
            return "undefined";
        }
    }

    private String getPlayerFromCookie(HttpCookie cookie) {
        try {
            Map<String, String> parsedCookieValues = Redirector.parseFormData(cookie.toString());
            String playerAndNickName = parsedCookieValues.get("player");
            return playerAndNickName.split(":")[0];
        } catch (IOException e) {
            e.printStackTrace();
            return "undefined";
        }
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
        updateBall(gameRoom);
        updateScore(gameRoom);
    }

    public void updateBall(GameRoom gameRoom) {
        Ball ball = gameRoom.getBall();

        ball.updateAngleIfOnBoardEdge();
        ball.updateAngleIfCollision(gameRoom.getFirstPlayer());
        ball.updateAngleIfCollision(gameRoom.getSecondPlayer());
        ball.updatePosition();
    }

    public void updateScore(GameRoom gameRoom) {
        final float player1BoardEdge = -10;
        final float player2BoardEdge = 810;

        float xPos = gameRoom.getBall().getxPos();

        if (xPos <= player1BoardEdge) {
            gameRoom.getSecondPlayer().addPoint();
            resetPositionsIn(gameRoom);
        } else if (xPos >= player2BoardEdge) {
            gameRoom.getFirstPlayer().addPoint();
            resetPositionsIn(gameRoom);
        }
    }

    private void resetPositionsIn(GameRoom gameRoom) {
        gameRoom.getBall().reset();
        gameRoom.getFirstPlayer().resetPosition();
        gameRoom.getSecondPlayer().resetPosition();
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

    public void sendResponse(HttpExchange exchange, String response) throws IOException {
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
