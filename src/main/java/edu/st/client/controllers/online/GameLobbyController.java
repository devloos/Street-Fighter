package edu.st.client.controllers.online;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.fxml.FXML;

public class GameLobbyController {
  public ListView<HBox> gameList = null;

  @FXML
  public void initialize() {
    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    hbox.setSpacing(100);
    hbox.getChildren().add(new Label("Game ID: Awesome ID"));
    hbox.getChildren().add(new Label("Players: 1"));
    hbox.getChildren().add(new Button("Join Game"));

    HBox hbox1 = new HBox();
    hbox1.setAlignment(Pos.CENTER);
    hbox1.setSpacing(100);
    hbox1.getChildren().add(new Label("Game ID: Connors Game"));
    hbox1.getChildren().add(new Label("Players: 1"));
    hbox1.getChildren().add(new Button("Join Game"));

    HBox hbox2 = new HBox();
    hbox2.setAlignment(Pos.CENTER);
    hbox2.setSpacing(100);
    hbox2.getChildren().add(new Label("Game ID: Carlos Game"));
    hbox2.getChildren().add(new Label("Players: 1"));
    hbox2.getChildren().add(new Button("Join Game"));

    HBox[] arr = { hbox, hbox1, hbox2 };
    gameList.getItems().addAll(arr);
  }
}
