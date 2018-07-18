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
            this.angle = calcReflectionAngle(player);
        }
    }

    public void updateAngleIfOnBoardEdge() {
        boolean shouldUpdate = isOnEdge();

        if (shouldUpdate) {
            normalizeAngle();
            bounceOffEdge();
        }
    }

    private boolean isOnEdge() {
        if (this.yPos <= 0) {
            this.yPos = 1;
            return true;
        } else if (this.yPos >= 480 - this.ballSize) {
            this.yPos = 480 - this.ballSize - 1;
            return true;
        } else {
            return false;
        }
    }

    private void bounceOffEdge() {
        if (this.angle >= 270) {
            // if aiming at top-right (270 - 359) then bounce bottom-right
            this.angle = 90 - (angle - 270);
        } else if (this.angle <= 90) {
            // if aiming at bottom-right (0 - 90) then bounce top-right
            this.angle = 360 - angle;
        } else if (this.angle <= 180) {
            // if aiming at bottom-left (90 - 180) then bounce top-left
            this.angle = 270 - (angle - 90);
        } else {
            // if aiming at top-left (180 - 270) then bounce bottom-left
            this.angle = 90 + (270 - angle);
        }
    }

    private void normalizeAngle() {
        this.angle = this.angle % 360;
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
