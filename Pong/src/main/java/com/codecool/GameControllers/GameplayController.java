package com.codecool.GameControllers;

import com.codecool.Model.GameRoom;

public class GameplayController {

    private final int maxScore;

    public GameplayController(int maxScore) {
        this.maxScore = maxScore;
    }

    void updateScores(GameRoom gameRoom) {
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

    boolean shouldUpdate(GameRoom gameRoom) {
        int p1Score = gameRoom.getFirstPlayer().getScore();
        int p2Score = gameRoom.getSecondPlayer().getScore();
        return ((p1Score < maxScore) && (p2Score < maxScore));
    }
}
