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
        String method = exchange.getRequestMethod();
        String cookieStr = exchange.getResponseHeaders().getFirst("Cookie");
        HttpCookie cookie;

        try {

            if(method.equals("GET")) {
                if(cookieStr != null) {
                    exchange.getResponseHeaders().add("Set-Cookie", cookieStr + ";Max-Age=0");
                }
                String html = loadSite("static/html/index.html");
                sendResponse(exchange, html);
            } else if (method.equals("POST")) {
                Map<String, String> parsedFormData = Redirector.getPostStringData(exchange);
                String nickName = parsedFormData.get("nickName");
                String roomId = parsedFormData.get("roomId");
                System.out.println(nickName);
                System.out.println(3);

                cookie = assignCookieToGameRoom(exchange, roomId, nickName);
                exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());

                String html = loadSite("static/html/index.html");
                sendResponse(exchange, html);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HttpCookie assignCookieToGameRoom(HttpExchange exchange, String roomId, String nickName) {
            Map<String, GameRoom> gameRooms = TestHandler.getGameRooms();
            HttpCookie cookie = null;
            Ball ball = new Ball(400f, 240f, 15f, 10f, 0.02f);
            Player player1;
            Player player2;
            GameRoom gameRoom = new GameRoom(ball, null, null);

        try {
            if(!isRoomCreated(roomId)) {
                cookie = createCookie(nickName, roomId, "P1");
                player1 = new Player(240f, 5f, nickName, 0, 60f, 10f);
                gameRoom.setFirstPlayer(player1);
                TestHandler.addToGameRooms(roomId, gameRoom);

            } else if((!isRoomCreated(roomId) && gameRooms.get(roomId).getSecondPlayer() == null)) {
                GameRoom gameRoomToJoin = gameRooms.get(roomId);
                cookie = createCookie(nickName, roomId, "P2");
                player2 = new Player(240f, 795f, nickName, 0, 60f, 10f);
                gameRoomToJoin.setSecondPlayer(player2);
                TestHandler.addToGameRooms(roomId, gameRoomToJoin);

            } else if((!isRoomCreated(roomId) && gameRooms.get(roomId).getSecondPlayer() != null)) {
                Redirector.redirect(exchange, "/pong");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
            return cookie;
    }

    private boolean isRoomCreated(String roomId) {
        Map<String, GameRoom> gameRooms = TestHandler.getGameRooms();

        return gameRooms.containsKey(roomId);
    }


    public HttpCookie createCookie(String nickName, String roomId, String player) {
        HttpCookie cookie = new HttpCookie("sessionId", roomId);
        cookie.setComment(player + ":" + nickName);

        return cookie;
    }
//    public  isCookieValid()

    public String loadSite(String fileName) {
        StringBuilder result = new StringBuilder();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine());
            }

        } catch (IOException | NullPointerException e) {
            return "404: File not found";
        }

        return result.toString();

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
