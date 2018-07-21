package com.codecool.Handlers;

import com.codecool.Helper.Redirector;
import com.codecool.Model.Ball;
import com.codecool.Model.GameRoom;
import com.codecool.Model.Player;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.Map;
import java.util.Scanner;

public class PongHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://192.168.10.193:8000/test");
        String method = exchange.getRequestMethod();
        String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");

        try {
            if (method.equals("GET")) {
                if (cookieStr != null) {
                    exchange.getResponseHeaders().add("Set-Cookie", cookieStr + ";Max-Age=0");
                }
                String html = loadSite("static/html/index.html");
                sendResponse(exchange, html);
            } else if (method.equals("POST")) {
                handlePost(exchange);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        HttpCookie cookie;
        Map<String, String> parsedFormData = Redirector.getPostStringData(exchange);
        String nickName = parsedFormData.get("nickName");
        String roomId = parsedFormData.get("roomId");

        cookie = assignCookieToGameRoom(exchange, roomId, nickName);
        String cookieHeader = convertCookieToString(cookie);
        exchange.getResponseHeaders().add("Set-Cookie", cookieHeader);

        sendResponse(exchange, "TODO");
    }

    private String convertCookieToString(HttpCookie cookie) {
        return "roomId=" + cookie.getValue() + "&player=" + cookie.getComment();
    }

    private HttpCookie assignCookieToGameRoom(HttpExchange exchange, String roomId, String nickName) {
        Map<String, GameRoom> gameRooms = GameHandler.getGameRooms();
        HttpCookie cookie = null;
        Ball ball = new Ball(400f, 240f, 0f, 8f, 0.0005f);
        Player player1;
        Player player2;
        GameRoom gameRoom = new GameRoom(ball, null, null);

        try {
            if (!isRoomCreated(roomId)) {
                cookie = createCookie(nickName, roomId, "P1");
                player1 = new Player(210f, 5f, nickName, 0, 60f, 10f);
                gameRoom.setFirstPlayer(player1);
                GameHandler.addToGameRooms(roomId, gameRoom);
            } else if ((isRoomCreated(roomId) && gameRooms.get(roomId).getSecondPlayer() == null)) {
                GameRoom gameRoomToJoin = gameRooms.get(roomId);
                cookie = createCookie(nickName, roomId, "P2");
                player2 = new Player(210f, 780f, nickName, 0, 60f, 10f);
                gameRoomToJoin.setSecondPlayer(player2);
                GameHandler.addToGameRooms(roomId, gameRoomToJoin);

            } else if ((!isRoomCreated(roomId) && gameRooms.get(roomId).getSecondPlayer() != null)) {
                Redirector.redirect(exchange, "/pong");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cookie;
    }

    private boolean isRoomCreated(String roomId) {
        Map<String, GameRoom> gameRooms = GameHandler.getGameRooms();

        return gameRooms.containsKey(roomId);
    }


    private HttpCookie createCookie(String nickName, String roomId, String player) {
        HttpCookie cookie = new HttpCookie("sessionId", roomId);
        cookie.setComment(player + ":" + nickName);
        return cookie;
    }

    private String loadSite(String fileName) {
        StringBuilder result = new StringBuilder();

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine());
            }

        } catch (NullPointerException e) {
            return "404: File not found";
        } catch (IOException e) {
            return "IO exception";
        }

        return result.toString();

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
