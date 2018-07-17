package com.codecool;

import com.codecool.Handlers.PongHandler;
import com.codecool.Handlers.StaticHandler;
import com.codecool.Handlers.TestHandler;
import com.codecool.Model.Ball;
import com.codecool.Model.GameRoom;
import com.codecool.Model.Player;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {

    public static GameRoom gameRoom;

    public static void main(String[] args) throws Exception {
        Player player = new Player(0f, 15f, "user", 0, 60f, 10f);
        Ball ball = new Ball(400f, 240f, 15f, 10f, 0.02f);
        gameRoom = new GameRoom(ball, player, player);
        GameRoom gameRoom = new GameRoom(ball, player1, player2);

        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/test", new TestHandler(gameRoom));
        server.createContext("/pong", new PongHandler());
        server.createContext("/static", new StaticHandler());
        server.setExecutor(null); // creates a default executor
        // start listening
        server.start();
    }
}
