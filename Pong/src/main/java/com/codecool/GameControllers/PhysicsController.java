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
        if (areColliding(ball, player)) {
            ball.setAngle(calcBounceAngle(ball, player));
        }
    }

    private boolean areColliding(Ball ball, Player player) {
        float ballLeft = ball.getxPos();
        float ballRight = ballLeft + ball.getBallSize();
        float ballTop = ball.getyPos();
        float ballBottom = ballTop + ball.getBallSize();

        float playerLeft = player.getRacketXPos();
        float playerRight = playerLeft + player.getRacketWidth();
        float playerTop = player.getRacketYPos();
        float playerBottom = playerTop + player.getRacketHeight();

        boolean collision = true;
        if ((ballBottom < playerTop) || (ballTop > playerBottom) || (ballRight < playerLeft) || (ballLeft > playerRight)) {
            collision = false;
        }
        return collision;
    }

    private float calcBounceAngle(Ball ball, Player player) {
        if (isOnLeftSide(ball)) {
            return bounceRightAngle(ball, player);
        } else {
            return bounceLeftAngle(ball, player);
        }
    }

    private boolean isOnLeftSide(Ball ball) {
        return ball.getxPos() <= boardWidth / 2;
    }

    private float bounceRightAngle(Ball ball, Player player) {
        float hitPositionAsPercent = calcHitPositionAsPercent(ball, player);
        return getAngleBetween(300f, 420f, hitPositionAsPercent);
    }

    private float bounceLeftAngle(Ball ball, Player player) {
        float hitPositionAsPercent =  1 - calcHitPositionAsPercent(ball, player);
        return getAngleBetween(120f, 240f, hitPositionAsPercent);
    }

    /**
     * relativeBallCenter is a variable that is in range from 0 (top of board)
     *      to player racket height (default 60).
     * @param ball - Ball colliding with player.
     * @param player - Object containing data about racket position.
     * @return place of collision described as percentage 0%:top of racket, 100%:bottom of racket.
     */
    private float calcHitPositionAsPercent(Ball ball, Player player) {
        float relativeBallCenter = ball.getBallCenterYPos() - player.getRacketYPos();
        return relativeBallCenter / player.getRacketHeight();
    }

    private float getAngleBetween(float minAngle, float maxAngle, float percentage) {
        return minAngle + ((maxAngle - minAngle) * percentage);
    }

    private void updatePosition(Ball ball) {

    }
}
