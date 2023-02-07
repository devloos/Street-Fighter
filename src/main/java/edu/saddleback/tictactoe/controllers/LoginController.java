package edu.saddleback.tictactoe.controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class LoginController {
  // all fxml properties
  public Button online_btn = null;
  public Button single_player_btn = null;
  public Button multiplayer_btn = null;
  public AnchorPane overlay = null;
  public ImageView background_image = null;

  public LoginController() {
    // readBackgrounds()
    // rngLoadBackgrounds() 
    //    with background_image.setImage(new Image("path/to/image.gif"))
  }

  @FXML
  public void initialize() {
    single_player_btn.setOnAction(event -> {
      overlay.setVisible(false);
    });

    online_btn.setOnAction(event -> {
      overlay.setVisible(false);
    });

    background_image.setImage(null);

    
  }

  public void readBackgorunds() {
    // read backgrounds from resources/data/backgrounds.txt
  }

  private ArrayList<Image> backgrounds = null;
}
