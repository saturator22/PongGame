package com.codecool.GameControllers;

import com.codecool.Model.Ball;
import com.codecool.Model.GameRoom;
import com.codecool.Model.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhysicsControllerTest {

    private PhysicsController testObj = new PhysicsController();
    private GameRoom gameRoom;

    private void createNewGameRoom() {
        Ball ball = new Ball(400f, 240f, 0f, 10f, 0f);
        Player player = new Player(0, "default");
        gameRoom = new GameRoom(ball, player, player);
    }

    @Test
    public void Should_UpdateBallPosition_When_UpdateCalled() {
        Test_Move_Right();
        Test_Move_Up();
        Test_Move_Diagonally_Up_Right();
    }

    private void Test_Move_Right() {
        createNewGameRoom();
        testObj.updateBall(gameRoom);
        float expectedX = 410f;
        float expectedY = 240f;
        assertEquals(expectedX, gameRoom.getBall().getxPos(), 0.01);
        assertEquals(expectedY, gameRoom.getBall().getyPos(), 0.01);
    }

    private void Test_Move_Up() {
        Ball ball = new Ball(400f, 240f, 90f, 10f, 1f);
        createNewGameRoom();
        gameRoom.setBall(ball);
        testObj.updateBall(gameRoom);

        float expectedX = 400f;
        float expectedY = 250f;
        assertEquals(expectedX, gameRoom.getBall().getxPos(), 0.01);
        assertEquals(expectedY, gameRoom.getBall().getyPos(), 0.01);
    }

    private void Test_Move_Diagonally_Up_Right() {
        Ball ball = new Ball(400f, 240f, 45f, 10f, 1f);
        createNewGameRoom();
        gameRoom.setBall(ball);
        testObj.updateBall(gameRoom);

        float expectedX = 407.07106781187f; // speed * cos(angle as radian)
        float expectedY = 247.07106781187f; // speed * sin(angle as radian)
        assertEquals(expectedX, gameRoom.getBall().getxPos(), 0.01);
        assertEquals(expectedY, gameRoom.getBall().getyPos(), 0.01);
    }
}