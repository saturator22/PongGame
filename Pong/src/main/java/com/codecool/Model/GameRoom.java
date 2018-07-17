package com.codecool.Model;

public class GameRoom {
    private Ball ball;
//    private final float racketHeight;
    private Player firstPlayer;
    private Player secondPlayer;

    public GameRoom(Ball ball, Player firstPlayer, Player secondPlayer) {
        this.ball = ball;
//        this.racketHeight = racketHeight;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

//    public float getRacketHeight() {
//        return racketHeight;
//    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
}
