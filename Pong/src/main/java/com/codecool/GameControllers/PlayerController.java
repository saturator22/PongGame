package com.codecool.GameControllers;

import com.codecool.Model.GameRoom;

public class PlayerController {

    void handleInputs(GameRoom gameRoom, String playerIdentifier, String direction) {
        if (playerIdentifier.equalsIgnoreCase("p1")) {
            gameRoom.getFirstPlayer().changePosition(direction);
        } else {
            gameRoom.getSecondPlayer().changePosition(direction);
        }
    }
}
