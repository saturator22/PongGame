package com.codecool.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameRoomTest {

    @Test
    public void Should_ReturnValidJSON_When_CorrectInput() {
        Player player = new Player(2.3f, "nickname");
        Ball ball = new Ball(0f, 0f, 90f, 10f, 0f);
        GameRoom gameRoom = new GameRoom(ball, player, player);
        String expected = "{\"ball\":{\"xPos\":0.0,\"yPos\":0.0,\"angle\":90.0,\"speed\":10.0," +
                    "\"speedIncreaseRate\":0.0,\"ballSize\":10.0}," +

                "\"firstPlayer\":{\"racketYPos\":2.3,\"racketXPos\":5.0,\"name\":\"nickname\",\"score\":0," +
                    "\"racketHeight\":60.0,\"racketWidth\":10.0}," +

                "\"secondPlayer\":{\"racketYPos\":2.3,\"racketXPos\":5.0,\"name\":\"nickname\",\"score\":0," +
                    "\"racketHeight\":60.0,\"racketWidth\":10.0}}";
        assertEquals(expected, gameRoom.toJSON());
    }
}