package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Game extends Application {

  @Override
  public void start(Stage window) throws Exception {
    window.setTitle("Tic Tac Toe");
    Scene scene = new Scene(new Label(), 640, 480);
    window.setScene(scene);
    window.show();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}