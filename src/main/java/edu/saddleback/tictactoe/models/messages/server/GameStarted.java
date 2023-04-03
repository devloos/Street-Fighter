package edu.saddleback.tictactoe.models.messages.server;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class GameStarted extends Message {
    public GameStarted() {
        super(MessageType.GameStarted);
    }
}
