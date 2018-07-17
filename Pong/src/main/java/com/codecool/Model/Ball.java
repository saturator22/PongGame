package com.codecool.Model;

public class Ball {
    private float xPos;
    private float yPos;
    private float angle;
    private float speed;

    public Ball(float xPos, float yPos, float angle, float speed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = angle;
        this.speed = speed;
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
}
