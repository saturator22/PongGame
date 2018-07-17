package com.codecool.Model;

public class Player {
    private float racketYPos;
    private final float racketXPos;
    private String name;
    private int score;
    private final float racketHeight;
    private final float racketWidth;

    public Player(float racketYPos, String name) {
        this.racketYPos = racketYPos;
        this.racketXPos = 5; // Default value
        this.name = name;
        this.score = 0; // Default value
        this.racketHeight = 60; // Default value
        this.racketWidth = 10; // Default value
    }

    public Player(float racketYPos, float racketXPos, String name, int score, float racketHeight, float racketWidth) {
        this.racketYPos = racketYPos;
        this.racketXPos = racketXPos;
        this.name = name;
        this.score = score;
        this.racketHeight = racketHeight;
        this.racketWidth = racketWidth;
    }

    public void addPoint() {
        score += 1;
    }

    public float getRacketYPos() {
        return racketYPos;
    }

    public void setRacketYPos(float racketYPos) {
        this.racketYPos = racketYPos;
    }

    public float getRacketXPos() {
        return racketXPos;
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

    public float getRacketHeight() {
        return racketHeight;
    }

    public float getRacketWidth() {
        return racketWidth;
    }
}
