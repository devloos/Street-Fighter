package edu.st.common.messages.client;

import java.util.UUID;

import edu.st.common.messages.Message;

public class JoinGame extends Message {
  private String username = null;
  private UUID gameId = null;

  public JoinGame() {
  }

  public JoinGame(String username, UUID gameId) {
    this.username = username;
    this.gameId = gameId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public UUID getGameId() {
    return gameId;
  }

  public void setGameId(UUID gameId) {
    this.gameId = gameId;
  }
}
