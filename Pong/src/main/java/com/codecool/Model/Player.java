package com.codecool.Model;

public class Player {
    private float racketYPos;
    private String name;
    private int score;
    private int racketLength;

    public Player(float racketYPos, String name) {
        this.racketYPos = racketYPos;
        this.name = name;
        this.racketLength = 60;
        this.score = 0;
    }

    public float getRacketYPos() {
        return racketYPos;
    }

    public void setRacketYPos(float racketYPos) {
        this.racketYPos = racketYPos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRacketLength() {
        return racketLength;
    }

    public void setRacketLength(int racketLength) {
        this.racketLength = racketLength;
    }
}
