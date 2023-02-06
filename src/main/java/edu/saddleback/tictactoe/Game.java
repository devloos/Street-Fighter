package edu.saddleback.tictactoe;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {
  // DOING THIS FOR NOW WE WILL CHANGE THIS
  //public static final String BASE_FILE_URI = "<ABSOLUTE FOLDER PATH>";
  public static final String FXML_FILE = "views/Game.fxml";

  @Override
  public void start(Stage window) throws Exception {
    window.setTitle("Tic Tac Toe");
    window.setResizable(false);

    FXMLLoader loader = new FXMLLoader(Game.class.getResource(FXML_FILE));
    //loader.setLocation(new URL("views/<FXML FILE>"));

    try {
      Scene scene = loader.<Scene>load();
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