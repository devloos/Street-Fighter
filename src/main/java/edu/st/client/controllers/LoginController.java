package edu.st.client.controllers;

import java.util.ArrayList;

import edu.st.client.Main;
import edu.st.client.services.LoginService;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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
    media = new MediaPlayer(new Media(Main.class.getResource("audio/title.mp3").toString()));
    media.setOnEndOfMedia(() -> {
      media.seek(Duration.ZERO); // reset playback position to the beginning
      media.play(); // start playing from the beginning
    });
    media.setVolume(0.1);
    media.play();
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
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/AvatarPicker.fxml"));
        AnchorPane pane = loader.<AnchorPane>load();
        Stage stage = (Stage) Window.getWindows().get(0);
        media.stop();
        stage.getScene().setRoot(pane);
      } catch (Exception e) {
        System.out.println(e);
      }
    });

    online_btn.setOnAction(event -> {
      try {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/GameLobby.fxml"));
        AnchorPane pane = loader.<AnchorPane>load();
        Stage stage = (Stage) Window.getWindows().get(0);
        media.stop();
        stage.getScene().setRoot(pane);
      } catch (Exception e) {
        System.out.println(e);
      }
    });
  }

  private ArrayList<Image> backgrounds = null;
  private MediaPlayer media = null;
}
