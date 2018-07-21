package com.codecool.Handlers;

import com.codecool.GameControllers.GameController;
import com.codecool.Helper.Redirector;
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
import java.util.Map;

public class GameHandler implements HttpHandler {
    private GameController gameController;

    public GameHandler(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        final String GET_METHOD = "GET";
        final String POST_METHOD = "POST";
        HttpCookie cookie;
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://192.168.10.193:8000/test");
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        String method = httpExchange.getRequestMethod();

        if(cookieStr != null) {
            cookie = HttpCookie.parse(cookieStr).get(0);
            String roomId = getRoomIdFromCookie(cookie);

            if (method.equals(GET_METHOD)) {
                handleGetMethod(httpExchange, roomId);

            } else if (method.equals(POST_METHOD)) {
                handlePostMethod(httpExchange, cookie, roomId);
            }
        } else {
            sendResponse(httpExchange, "{}");
        }

    }

    private void handlePostMethod(HttpExchange httpExchange, HttpCookie cookie, String roomId) {
        String player = getPlayerFromCookie(cookie);
        TextInput input = readAndParseJSON(httpExchange);
        gameController.handleInputs(roomId, player, input);
        sendResponse(httpExchange, "");
    }

    private void handleGetMethod(HttpExchange httpExchange, String roomId) {
        GameRoom gameRoom = gameController.getGameRooms().get(roomId);
        if (gameRoom != null) {
            gameController.updateGameRoom(gameRoom);
            sendResponse(httpExchange, gameRoom.toJSON());
        } else {
            sendResponse(httpExchange, "{}");
        }
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

    private TextInput readAndParseJSON(HttpExchange exchange) {
        try {
            String jsonString = readExchangeContent(exchange);
            Gson gson = new Gson();
            return gson.fromJson(jsonString, TextInput.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readExchangeContent(HttpExchange exchange) {
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

    private void sendResponse(HttpExchange exchange, String response) {
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
