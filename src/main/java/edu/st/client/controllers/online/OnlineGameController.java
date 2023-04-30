package edu.st.client.controllers.online;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OnlineGameController {
  public Label player1 = null;
  public Label player2 = null;

  public OnlineGameController(String p1, String p2) {
    this.p1 = p1;
    this.p2 = p2;
  }

  @FXML
  public void initialize() {
    player1.setText(p1);
    player2.setText(p2);
  }

  public String p1 = null;
  public String p2 = null;
}
