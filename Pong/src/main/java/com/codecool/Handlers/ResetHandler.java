package com.codecool.Handlers;

import com.codecool.GameControllers.GameController;
import com.codecool.Helper.Redirector;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;

public class ResetHandler implements HttpHandler {

    private GameController gameController;

    public ResetHandler(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
        String roomId = getRoomIdFromCookie(cookie);
        gameController.resetGameRoom(roomId);
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
}
