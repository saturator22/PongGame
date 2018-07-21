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
        if (isOnEdge(ball)) {
            normailzeBallPosition(ball);
            normalizeAngle(ball);
            updateAngleAfterBounce(ball);
        }
    }

    private boolean isOnEdge(Ball ball) {
        return (ball.getyPos() <= 0 || ball.getyPos() >= boardHeight - ball.getBallSize());
    }

    private void normailzeBallPosition(Ball ball) {
        if (ball.getyPos() <= 0) {
            ball.setyPos(0);
        } else {
            ball.setyPos(boardHeight - ball.getBallSize());
        }
    }

    /**
     * Method prevents ball from having angle out of range <0-360)
     * @param ball - Ball object to be updated
     */
    private void normalizeAngle(Ball ball) {
        ball.setAngle(ball.getAngle() % 360);
    }

    private void updateAngleAfterBounce(Ball ball) {
        ball.setAngle(360 - ball.getAngle());
    }

    private void updateAngleIfCollision(Ball ball, Player player) {

    }

    private void updatePosition(Ball ball) {

    }
}
