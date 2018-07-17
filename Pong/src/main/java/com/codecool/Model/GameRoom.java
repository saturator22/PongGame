package com.codecool.Model;

import com.google.gson.Gson;

public class GameRoom {
    private Ball ball;
    private final float racketHeight;
    private Player firstPlayer;
    private Player secondPlayer;

    public GameRoom(Ball ball, float racketHeight, Player firstPlayer, Player secondPlayer) {
        this.ball = ball;
        this.racketHeight = racketHeight;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public float getRacketHeight() {
        return racketHeight;
    }

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

    public String toJSON() {
        Gson gson = getNewGson();
        String json = gson.toJson(this);
        return json;
    }

    public Gson getNewGson() {
        return new Gson();
    }
}
