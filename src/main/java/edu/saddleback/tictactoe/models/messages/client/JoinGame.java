package edu.saddleback.tictactoe.models.messages.client;

import edu.saddleback.tictactoe.models.messages.Message;

public class JoinGame extends Message {
  public String username = null;
  public int game_lobby_id;
}
