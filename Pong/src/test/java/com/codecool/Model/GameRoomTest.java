package com.codecool.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameRoomTest {

    @Test
    public void Should_ReturnValidJSON_When_CorrectInput() {
        Player player = new Player(2.3f, "nickname");
        Ball ball = new Ball(0f, 0f, 90f, 10f);
        GameRoom gameRoom = new GameRoom(ball, 60f, player, player);
        String expected = "{\"ball\":{\"xPos\":0.0,\"yPos\":0.0,\"angle\":90.0,\"speed\":10.0}," +
                "\"racketHeight\":60.0,\"firstPlayer\":{\"racketYPos\":2.3,\"name\":\"nickname\",\"score\":0}," +
                "\"secondPlayer\":{\"racketYPos\":2.3,\"name\":\"nickname\",\"score\":0}}";
        assertEquals(expected, gameRoom.toJSON());
    }
}