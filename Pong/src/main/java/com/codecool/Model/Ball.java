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

    public void updateAngleIfCollision(Player player) {
        if (isCollidingWith(player)) {
            float updatedAngle = calcReflectionAngle(player);
            this.angle = updatedAngle;
        }
    }

    public void updateAngleIfOnBoardEdge() {
        if (this.yPos <= 1 || this.yPos >= 479) {
            this.angle += 90f;
        }
    }

    public boolean isCollidingWith(Player player) {
        float ballLeft = this.xPos;
        float ballRight = ballLeft + this.ballSize;
        float ballTop = this.yPos;
        float ballBottom = ballTop + this.ballSize;

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

    public float calcReflectionAngle(Player player) {
        float playerTop = player.getRacketYPos();
        float playerBottom = playerTop + player.getRacketHeight();

        if (isOnLeftSide()) {
            return bounceRightAngle(playerTop, playerBottom);
        } else {
            return bounceLeftAngle(playerTop, playerBottom);
        }
    }

    public boolean isOnLeftSide() {
        return this.xPos <= 400;
    }

    public float bounceRightAngle(float playerTop, float playerBottom) {
        float hitPositionAsPercent = calcHitPositionAsPercent(playerTop, playerBottom);
        return getAngleBetween(280f, 440f, hitPositionAsPercent);
    }

    public float bounceLeftAngle(float playerTop, float playerBottom) {
        float hitPositionAsPercent =  1 - calcHitPositionAsPercent(playerTop, playerBottom);
        return getAngleBetween(100f, 260f, hitPositionAsPercent);
    }

    public float calcHitPositionAsPercent(float playerTop, float playerBottom) {
        float relativeBallCenter = getBallCenterYPos() - playerTop;
        float relativePlayerBottom = playerBottom - playerTop;
        return relativeBallCenter / relativePlayerBottom;
    }

    public float getBallCenterYPos() {
        return this.yPos + (this.ballSize / 2);
    }

    public void updatePosition() {
        double newX = speed * Math.cos(getAngleAsRadian());
        double newY = speed * Math.sin(getAngleAsRadian());
        this.xPos += (float) newX;
        this.yPos += (float) newY;
        this.speed += this.speedIncreaseRate;
    }

    public float getAngleAsRadian() {
        double radian = angle * Math.PI / 180;
        return (float) radian;
    }

    public float getAngleBetween(float minAngle, float maxAngle, float percentage) {
        return minAngle + ((maxAngle - minAngle) * percentage);
    }
}
