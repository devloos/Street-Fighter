package edu.st.client;

import java.io.IOException;

import edu.st.common.messages.Message;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Game extends Application {
  public static final String FXML_FILE = "views/Login.fxml";

  @Override
  public void start(Stage window) throws Exception {
    window.setTitle("Tic Tac Toe");
    window.setResizable(false);

    FXMLLoader loader = new FXMLLoader(Game.class.getResource(FXML_FILE));

    try {
      AnchorPane pane = loader.<AnchorPane>load();
      Scene scene = new Scene(pane);
      window.setScene(scene);
      window.show();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}