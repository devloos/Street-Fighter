package edu.st.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.st.common.Util;
import edu.st.common.messages.GameResult;
import edu.st.common.messages.Message;
import edu.st.common.messages.Packet;
import edu.st.common.messages.client.BackToMainMenu;
import edu.st.common.messages.client.JoinGame;
import edu.st.common.messages.client.MakeMove;
import edu.st.common.messages.client.PlayAgain;
import edu.st.common.messages.client.PlayerAvatarChange;
import edu.st.common.messages.server.GameEnded;
import edu.st.common.messages.server.GameStarted;
import edu.st.common.messages.server.MoveMade;
import edu.st.common.models.Game;
import edu.st.common.models.GamePair;
import edu.st.common.models.Token;
import javafx.util.Pair;

public class GameController extends Thread {
  private static volatile ArrayList<Pair<Socket, Packet<Message>>> jobs = new ArrayList<>();
  private static volatile ArrayList<Game> currentGames = new ArrayList<>();

  @Override
  public void run() {
    while (true) {
      if (jobs.isEmpty()) {
        continue;
      }

      Socket socket = jobs.get(0).getKey();
      Packet<Message> packet = jobs.get(0).getValue();
      Message message = packet.getMessage();
      String channel = packet.getChannel();

      // eventually move to bottom
      // doing job
      deleteJob();
      Game game = currentGames.stream().filter(el -> el.getGameId().toString().equals(channel)).findFirst()
          .orElse(null);

      if (game == null) {
        System.out.println("GAME NOT FOUND IN GAME CONTROLLER");
        continue;
      }

      if (message.getType().contains("PlayerAvatarChange")) {
        PlayerAvatarChange msg = (PlayerAvatarChange) message;
        if (socket == game.getHostSocket()) {
          Util.println(game.getPlayerSocket(), msg, channel);
        } else {
          Util.println(game.getHostSocket(), msg, channel);
        }
      }

      if (message.getType().contains("PlayerReady")) {
        if (socket == game.getHostSocket()) {
          game.hostReady = true;
        } else {
          game.playerReady = true;
        }

        if (game.bothPlayersReady()) {
          Util.println(game.getHostSocket(), new GameStarted(), channel);
          Util.println(game.getPlayerSocket(), new GameStarted(), channel);
        }
      }

      if (message.getType().contains("MakeMove")) {
        MakeMove msg = (MakeMove) message;

        Token currentPlayer = game.getCurrentPlayer();

        // make sure its current player
        if (currentPlayer == Token.X && socket != game.getHostSocket()) {
          continue;
        }

        // make sure its current player
        if (currentPlayer == Token.Y && socket != game.getPlayerSocket()) {
          continue;
        }

        Integer row = msg.getRow();
        Integer col = msg.getCol();

        ArrayList<ArrayList<Token>> board = game.getBoard();

        if (board.get(row).get(col) != null) {
          continue;
        }

        board.get(row).set(col, currentPlayer);

        if (Util.isWinner(board)) {
          boolean hostWon = currentPlayer == Token.X;
          GameResult gameResult = hostWon ? GameResult.Host : GameResult.Player;

          Util.println(game.getHostSocket(), new GameEnded(gameResult, row, col), channel);
          Util.println(game.getPlayerSocket(), new GameEnded(gameResult, row, col), channel);
          continue;
        }

        if (Util.isBoardFull(board)) {
          GameEnded gameEnded = new GameEnded(GameResult.Tie, row, col);
          Util.println(game.getHostSocket(), gameEnded, channel);
          Util.println(game.getPlayerSocket(), gameEnded, channel);
          continue;
        }

        MoveMade moveMade = new MoveMade(row, col, currentPlayer);
        game.updateCurrentPlayer();
        Util.println(game.getHostSocket(), moveMade, channel);
        Util.println(game.getPlayerSocket(), moveMade, channel);
      }

      if (message.getType().contains("PlayAgain")) {
        // make sure its current player
        if (socket == game.getHostSocket()) {
          game.hostPlayAgain = true;
        } else {
          game.playerPlayAgain = true;
        }

        if (game.playAgain()) {
          game.resetGame();
          Util.println(game.getHostSocket(), new PlayAgain(), channel);
          Util.println(game.getPlayerSocket(), new PlayAgain(), channel);
        }
      }

      if (message.getType().contains("BackToMainMenu")) {
        currentGames.remove(game);
        Util.println(game.getHostSocket(), new BackToMainMenu(), channel);
        Util.println(game.getPlayerSocket(), new BackToMainMenu(), channel);
      }
    }
  }

  public static synchronized void addJob(Pair<Socket, Packet<Message>> pair) {
    jobs.add(pair);
  }

  public static synchronized Game findGame(JoinGame joinGame) {
    Optional<Game> option = currentGames
        .stream()
        .filter(el -> el.getGameId().equals(joinGame.getGameId()))
        .findFirst();

    if (option.isPresent()) {
      return option.get();
    }

    return null;
  }

  public static synchronized void addGame(Game game) {
    currentGames.add(game);
  }

  public static synchronized ArrayList<GamePair> getGames() {
    ArrayList<Game> notLiveGames = currentGames.stream()
        .filter(el -> !el.isLive())
        .collect(Collectors.toCollection(ArrayList::new));

    ArrayList<GamePair> games = new ArrayList<>();
    for (Game game : notLiveGames) {
      games.add(new GamePair(game.getGameId(), game.getHostname()));
    }

    return games;
  }

  private static synchronized void deleteJob() {
    jobs.remove(0);
  }
}
