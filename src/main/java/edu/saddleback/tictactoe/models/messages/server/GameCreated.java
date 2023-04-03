package edu.saddleback.tictactoe.models.messages.server;

import edu.saddleback.tictactoe.models.messages.Message;
import edu.saddleback.tictactoe.models.messages.MessageType;

public class GameCreated extends Message {
  GameCreated(int game_lobby_id) {
    super(MessageType.GameCreated);
    this.game_lobby_id = game_lobby_id;
  }

  public int getGameLobbyId() {
    return game_lobby_id;
  }

  public void setGameLobbyId(int game_lobby_id) {
    this.game_lobby_id = game_lobby_id;
  }

  private int game_lobby_id;
}
