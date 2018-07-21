package com.codecool.Model;

public class Ball {
    private float xPos;
    private float yPos;
    private float angle;
    private float speed;
    private final float speedIncreaseRate;
    private final float ballSize;

    public Ball(float xPos, float yPos, float angle, float speed, float speedIncreaseRate) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = angle;
        this.speed = speed;
        this.speedIncreaseRate = speedIncreaseRate;
        this.ballSize = 10;
    }

    public Ball(float xPos, float yPos, float angle, float speed, float speedIncreaseRate, float ballSize) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = angle;
        this.speed = speed;
        this.speedIncreaseRate = speedIncreaseRate;
        this.ballSize = ballSize;
    }

    public void reset() {
        this.xPos = 400;
        this.yPos = 240;
        this.speed = 10;
        this.angle = 0;
    }

    public float getBallSize() {
        return ballSize;
    }

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeedIncreaseRate() {
        return speedIncreaseRate;
    }

    public float getBallCenterYPos() {
        return this.yPos + (this.ballSize / 2);
    }
}
