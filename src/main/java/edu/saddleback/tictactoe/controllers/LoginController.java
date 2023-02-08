package edu.saddleback.tictactoe.controllers;

import edu.saddleback.tictactoe.Game;
import edu.saddleback.tictactoe.services.LoginService;

import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.Scene;

public class LoginController {
  // all fxml properties
  public Button online_btn = null;
  public Button single_player_btn = null;
  public Button multiplayer_btn = null;
  public Button return_btn = null;
  public AnchorPane overlay = null;
  public AnchorPane root = null;
  public ImageView background_image = null;

  public LoginController() {
    backgrounds = new ArrayList<Image>();
    LoginService.readBackgrounds(backgrounds);
  }

  @FXML
  public void initialize() {
    background_image.setImage(LoginService.getRandomBackground(backgrounds));

    return_btn.setOnAction(event -> {
        overlay.setVisible(false);
    });

    single_player_btn.setOnAction(event -> {
      overlay.setVisible(true);
    });

    multiplayer_btn.setOnAction(event -> {
      try {
        FXMLLoader loader = new FXMLLoader(Game.class.getResource("views/Game.fxml"));
        Scene scene = loader.<Scene>load();
        Stage window = (Stage)root.getScene().getWindow();
        window.setScene(scene);
        window.show();
      } catch (Exception e) {
        System.out.println(e);
      }
    });

    online_btn.setOnAction(event -> {
      overlay.setVisible(true);
    });
  }

  private ArrayList<Image> backgrounds = null;
}
