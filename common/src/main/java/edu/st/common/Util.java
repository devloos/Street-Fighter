package edu.st.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import edu.st.common.messages.Message;
import edu.st.common.messages.Packet;
import edu.st.common.models.Token;
import edu.st.common.serialize.SerializerFactory;

public class Util {
  public static boolean isWinner(ArrayList<ArrayList<Token>> board) {
    Token t1 = null;
    Token t2 = null;
    Token t3 = null;
    for (ArrayList<Token> row : board) {
      t1 = row.get(0);
      t2 = row.get(1);
      t3 = row.get(2);

      if (checkTiles(t1, t2, t3)) {
        return true;
      }
    }

    for (int col = 0; col < board.size(); ++col) {
      t1 = board.get(0).get(col);
      t2 = board.get(1).get(col);
      t3 = board.get(2).get(col);

      if (checkTiles(t1, t2, t3)) {
        return true;
      }
    }

    t1 = board.get(0).get(0);
    t2 = board.get(1).get(1);
    t3 = board.get(2).get(2);
    if (checkTiles(t1, t2, t3)) {
      return true;
    }

    t1 = board.get(0).get(2);
    t2 = board.get(1).get(1);
    t3 = board.get(2).get(0);
    if (checkTiles(t1, t2, t3)) {
      return true;
    }

    return false;
  }

  public static boolean isBoardFull(ArrayList<ArrayList<Token>> board) {
    for (ArrayList<Token> row : board) {
      if (row.contains(null)) {
        return false;
      }
    }
    return true;
  }

  public static boolean checkTiles(Token t1, Token t2, Token t3) {
    if (t1 == null || t2 == null || t3 == null) {
      return false;
    }

    if (t1 == t2 && t2 == t3) {
      return true;
    }

    return false;
  }

  public static void println(Socket socket, Message message, String channel) {
    Packet<Message> packet = new Packet<Message>(message, channel);
    try {
      PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
      output.println(SerializerFactory.getSerializer().serialize(packet));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Packet<Message> deserialize(String message) {
    return SerializerFactory.getSerializer().deserialize(message);
  }
}
