package src;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {
  // DOING THIS FOR NOW WE WILL CHANGE THIS
  public static final String BASE_FILE_URI = "<ABSOLUTE FOLDER PATH>";

  @Override
  public void start(Stage window) throws Exception {
    window.setTitle("Tic Tac Toe");
    window.setResizable(false);

    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(new URL(BASE_FILE_URI + "/src/views/<FXML FILE>"));

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