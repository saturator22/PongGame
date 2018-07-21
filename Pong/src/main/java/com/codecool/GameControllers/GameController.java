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

    public GameController(PhysicsController physicsController, PlayerController playerController) {
        this.physicsController = physicsController;
        this.playerController = playerController;
    }

    public void updateGameRoom(GameRoom gameRoom) {
        physicsController.updateBall(gameRoom);
        updateScores(gameRoom);
    }

    public void handleInputs(String roomId, String playerIdentifier, TextInput input) {
        GameRoom gameRoom = gameRooms.get(roomId);
        if (gameRoom != null) {
            playerController.handleInputs(gameRoom, playerIdentifier, input.toString());
        }
    }

    public void addToGameRooms(String roomId, GameRoom gameRoom) {
        gameRooms.put(roomId, gameRoom);
    }

    public void resetGameRoom(String roomId) {
        GameRoom gameRoom = gameRooms.get(roomId);
        gameRoom.getFirstPlayer().resetGamePlayStats();
        gameRoom.getSecondPlayer().resetGamePlayStats();
        Ball newBall = new Ball(400f, 240f, 0f, 10f, 0.02f);
        gameRoom.setBall(newBall);
    }

    public Map<String, GameRoom> getGameRooms() {
        return this.gameRooms;
    }

    private void updateScores(GameRoom gameRoom) {
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
}
