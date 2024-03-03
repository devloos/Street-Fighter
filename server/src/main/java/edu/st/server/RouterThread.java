package edu.st.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import edu.st.common.Util;
import edu.st.common.messages.Message;
import edu.st.common.messages.Packet;
import edu.st.common.messages.Subscribe;
import edu.st.common.messages.client.CreateGame;
import edu.st.common.messages.client.JoinGame;
import edu.st.common.messages.server.GameList;
import edu.st.common.messages.server.GameStarted;
import edu.st.common.models.Game;
import javafx.util.Pair;

/**
 * Server Thread processing each connected client
 */
public class RouterThread extends Thread {

  private static volatile ArrayList<Pair<Socket, Packet<Message>>> jobs = new ArrayList<>();
  private HashMap<String, ArrayList<Socket>> map = new HashMap<String, ArrayList<Socket>>();

  public RouterThread() {
  }

  @Override
  public void run() {
    while (true) {
      if (jobs.isEmpty()) {
        continue;
      }

      Socket socket = jobs.get(0).getKey();
      Packet<Message> packet = jobs.get(0).getValue();
      Message message = packet.getMessage();

      if (message.getType().contains("Subscribe")) {
        Subscribe subscribe = (Subscribe) message;
        for (String channelToSub : subscribe.getChannels()) {
          getSockets(channelToSub).add(socket);

          if (channelToSub.equals("/gamelist")) {
            GameList gamelist = new GameList(GameController.getGames());
            for (Socket s : getSockets("/gamelist")) {
              Util.println(s, gamelist, "/gamelist");
            }
          }
        }
      }

      if (message.getType().contains("CreateGame")) {
        CreateGame createGame = (CreateGame) message;
        Game game = new Game(UUID.randomUUID(), createGame.getHostname(), socket, null, null);

        GameController.addGame(game);

        GameList gamelist = new GameList(GameController.getGames());
        for (Socket s : getSockets("/gamelist")) {
          Util.println(s, gamelist, "/gamelist");
        }

        ArrayList<Socket> list = new ArrayList<>();
        list.add(socket);
        map.put(game.getGameId().toString(), list);
      }

      if (message.getType().contains("JoinGame")) {
        JoinGame joinGame = (JoinGame) message;

        Game game = GameController.findGame(joinGame);

        String gameId = game.getGameId().toString();

        game.setPlayername(joinGame.getUsername());
        game.setPlayerSocket(socket);
        game.setLive(true);
        ArrayList<Socket> list = getSockets(gameId);
        list.add(socket);

        // host
        Util.println(list.get(0), new GameStarted(joinGame.getUsername(), game.getGameId(), true), gameId);
        // player
        Util.println(list.get(1), new GameStarted(game.getHostname(), game.getGameId(), false), gameId);

        GameList gamelist = new GameList(GameController.getGames());
        for (Socket s : getSockets("/gamelist")) {
          Util.println(s, gamelist, "/gamelist");
        }
      }

      deleteJob();
    }
  }

  public static synchronized void addJob(Pair<Socket, Packet<Message>> pair) {
    jobs.add(pair);
  }

  private static synchronized void deleteJob() {
    jobs.remove(0);
  }

  private ArrayList<Socket> getSockets(String channel) {
    if (map.get(channel) == null) {
      map.put(channel, new ArrayList<Socket>());
    }
    return map.get(channel);
  }
}
