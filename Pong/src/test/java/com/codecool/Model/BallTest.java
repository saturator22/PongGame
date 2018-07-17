package com.codecool.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BallTest {

    @Test
    public void Should_UpdateBallPosition_When_UpdateCalled() {
        Test_Move_Right();
        Test_Move_Up();
        Test_Move_Diagonally_Up_Right();
    }

    private void Test_Move_Right() {
        Ball testObj = new Ball(0f, 0f, 0f, 10f, 1f);
        testObj.updatePosition();
        float expectedX = 10f;
        float expectedY = 0f;
        assertEquals(expectedX, testObj.getxPos(), 0.01);
        assertEquals(expectedY, testObj.getyPos(), 0.01);
    }

    private void Test_Move_Up() {
        Ball testObj = new Ball(0f, 0f, 90f, 10f, 1f);
        testObj.updatePosition();
        float expectedX = 0f;
        float expectedY = 10f;
        assertEquals(expectedX, testObj.getxPos(), 0.01);
        assertEquals(expectedY, testObj.getyPos(), 0.01);
    }

    private void Test_Move_Diagonally_Up_Right() {
        Ball testObj = new Ball(0f, 0f, 45f, 10f, 1f);
        testObj.updatePosition();
        float expectedX = 7.07106781187f; // speed * cos(angle as radian)
        float expectedY = 7.07106781187f; // speed * sin(angle as radian)
        assertEquals(expectedX, testObj.getxPos(), 0.01);
        assertEquals(expectedY, testObj.getyPos(), 0.01);
    }
}