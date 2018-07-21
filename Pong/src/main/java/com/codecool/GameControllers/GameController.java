package com.codecool.GameControllers;

import com.codecool.Model.Ball;
import com.codecool.Model.GameRoom;
import com.codecool.Model.TextInput;

import java.util.HashMap;
import java.util.Map;

public class GameController {

    private Map<String, GameRoom> gameRooms = new HashMap<>();
    private PhysicsController physicsController;
    private PlayerController playerController;
    private GameplayController gameplayController;

    public GameController(PhysicsController physicsController, PlayerController playerController, GameplayController gameplayController) {
        this.physicsController = physicsController;
        this.playerController = playerController;
        this.gameplayController = gameplayController;
    }

    public void updateGameRoom(GameRoom gameRoom) {
        if (gameplayController.shouldUpdate(gameRoom)) {
            physicsController.updateBall(gameRoom);
            gameplayController.updateScores(gameRoom);
        }
    }

    public void handleInputs(String roomId, String playerIdentifier, TextInput input) {
        GameRoom gameRoom = gameRooms.get(roomId);
        if (gameRoom != null) {
            playerController.handleInputs(gameRoom, playerIdentifier, input.toString());
        }
    }

    public void resetGameRoom(String roomId) {
        GameRoom gameRoom = gameRooms.get(roomId);
        gameRoom.getFirstPlayer().resetGamePlayStats();
        gameRoom.getSecondPlayer().resetGamePlayStats();
        Ball newBall = new Ball(400f, 240f, 0f, 10f, 0.02f);
        gameRoom.setBall(newBall);
    }

    public void addToGameRooms(String roomId, GameRoom gameRoom) {
        gameRooms.put(roomId, gameRoom);
    }

    public Map<String, GameRoom> getGameRooms() {
        return this.gameRooms;
    }
}
