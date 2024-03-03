package edu.st.common.models;

import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

public class Game {
  public boolean hostReady = false;
  public boolean playerReady = false;
  public boolean hostPlayAgain = false;
  public boolean playerPlayAgain = false;

  public Game() {
  }

  public Game(UUID gameId, String hostname, Socket hostSocket, String playername, Socket playerSocket) {
    this.gameId = gameId;
    this.hostname = hostname;
    this.hostSocket = hostSocket;
    this.playername = playername;
    this.playerSocket = playerSocket;

    board = new ArrayList<ArrayList<Token>>();
    for (int i = 0; i < 3; ++i) {
      board.add(new ArrayList<Token>());
      for (int j = 0; j < 3; ++j) {
        board.get(i).add(null);
      }
    }
  }

  public void updateCurrentPlayer() {
    switch (currentPlayer) {
      case X: {
        setCurrentPlayer(Token.Y);
        break;
      }
      case Y: {
        setCurrentPlayer(Token.X);
        break;
      }
      default: {
        break;
      }
    }
  }

  public void resetGame() {
    // clear state
    for (ArrayList<Token> row : board) {
      for (int i = 0; i < row.size(); ++i) {
        row.set(i, null);
      }
    }

    hostPlayAgain = false;
    playerPlayAgain = false;
    // Player one starts again
    setCurrentPlayer(Token.X);
  }

  public boolean playAgain() {
    return hostPlayAgain && playerPlayAgain;
  }

  // ! Getters and Setters

  public UUID getGameId() {
    return gameId;
  }

  public void setGameId(UUID gameId) {
    this.gameId = gameId;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public Socket getHostSocket() {
    return hostSocket;
  }

  public void setHostSocket(Socket hostSocket) {
    this.hostSocket = hostSocket;
  }

  public String getPlayername() {
    return playername;
  }

  public void setPlayername(String playername) {
    this.playername = playername;
  }

  public Socket getPlayerSocket() {
    return playerSocket;
  }

  public void setPlayerSocket(Socket playerSocket) {
    this.playerSocket = playerSocket;
  }

  public Token getCurrentPlayer() {
    return currentPlayer;
  }

  public void setCurrentPlayer(Token currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public boolean isLive() {
    return live;
  }

  public void setLive(boolean live) {
    this.live = live;
  }

  public ArrayList<ArrayList<Token>> getBoard() {
    return board;
  }

  public void setBoard(ArrayList<ArrayList<Token>> board) {
    this.board = board;
  }

  public boolean bothPlayersReady() {
    return hostReady && playerReady;
  }

  private UUID gameId = null;
  private String hostname = null;
  private Socket hostSocket = null;
  private String playername = null;
  private Socket playerSocket = null;
  private Token currentPlayer = Token.X;
  private boolean live = false;
  private ArrayList<ArrayList<Token>> board = null;
}
