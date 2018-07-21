package com.codecool.GameControllers;

import com.codecool.Model.Ball;
import com.codecool.Model.GameRoom;
import com.codecool.Model.Player;

public class PhysicsController {

    private final int boardWidth;
    private final int boardHeight;

    public PhysicsController(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }

    public PhysicsController() {
        this.boardWidth = 800;
        this.boardHeight = 480;
    }

    public void updateBall(GameRoom gameRoom) {
        updateBallAngle(gameRoom.getBall(), gameRoom.getFirstPlayer(), gameRoom.getSecondPlayer());
        updatePosition(gameRoom.getBall());
    }

    private void updateBallAngle(Ball ball, Player player1, Player player2) {
        updateAngleIfOnBoardEdge(ball);
        updateAngleIfCollision(ball, player1);
        updateAngleIfCollision(ball, player2);
    }

    private void updateAngleIfOnBoardEdge(Ball ball) {

    }

    private void updateAngleIfCollision(Ball ball, Player player) {

    }

    private void updatePosition(Ball ball) {

    }
}
