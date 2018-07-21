package com.codecool;

import com.codecool.GameControllers.PhysicsController;
import com.codecool.Handlers.PongHandler;
import com.codecool.Handlers.ResetHandler;
import com.codecool.Handlers.StaticHandler;
import com.codecool.Handlers.TestHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws Exception {

        PhysicsController physicsController = new PhysicsController(800, 480);

        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/test", new TestHandler(physicsController));
        server.createContext("/reset", new ResetHandler());
        server.createContext("/pong", new PongHandler());
        server.createContext("/static", new StaticHandler());
        // start listening
        server.start();
    }
}
