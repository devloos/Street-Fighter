package edu.st.client;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
  @Override
  public void start(Stage window) throws Exception {
    window.setTitle("Tic Tac Toe");
    window.setResizable(false);

    window.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent e) {
        Platform.exit();
        System.exit(0);
      }
    });

    FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/Login.fxml"));

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