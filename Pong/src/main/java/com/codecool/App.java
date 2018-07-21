package com.codecool;

import com.codecool.GameControllers.*;
import com.codecool.Handlers.PongHandler;
import com.codecool.Handlers.ResetHandler;
import com.codecool.Handlers.StaticHandler;
import com.codecool.Handlers.GameHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws Exception {

        PhysicsController physicsController = new PhysicsController(800, 480);
        PlayerController playerController = new PlayerController();
        GameplayController gameplayController = new GameplayController(20);
        GameController gameController = new GameController(physicsController, playerController, gameplayController);

        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/game", new GameHandler(gameController));
        server.createContext("/reset", new ResetHandler(gameController));
        server.createContext("/pong", new PongHandler(gameController));
        server.createContext("/static", new StaticHandler());
        // start listening
        server.start();
    }
}
