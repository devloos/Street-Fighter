package edu.saddleback.tictactoe.models.messages.server;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class GameNotStarted extends Message {
    public GameNotStarted() {
        super(MessageType.GameNotStarted);
    }
}
