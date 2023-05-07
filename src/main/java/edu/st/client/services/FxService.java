package edu.st.client.services;

import java.io.IOException;

import edu.st.client.Main;
import edu.st.client.controllers.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class FxService {
  // THIS CUTS ALL CONNECTION WITH CURRENT CONTROLLER RUN LAST
  public static void switchViews(String viewPath, BaseController controller) {
    try {
      FXMLLoader loader = new FXMLLoader(Main.class.getResource(viewPath));
      if (controller != null) {
        loader.setController(controller);
      }
      Stage stage = (Stage) Window.getWindows().get(0);
      AnchorPane pane = loader.<AnchorPane>load();
      stage.getScene().setRoot(pane);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void setMedia(String mediaPath) {
    if (mediaPlayer instanceof MediaPlayer) {
      mediaPlayer.stop();
    }

    mediaPlayer = new MediaPlayer(new Media(Main.class.getResource(mediaPath).toString()));
    mediaPlayer.setOnEndOfMedia(() -> {
      mediaPlayer.seek(Duration.ZERO); // reset playback position to the beginning
      mediaPlayer.play(); // start playing from the beginning
    });
    mediaPlayer.setVolume(0.1);
  }

  public static void playMedia() {
    // mediaPlayer.play();
  }

  public static void stopMedia() {
    mediaPlayer.stop();
  }

  private static MediaPlayer mediaPlayer = null;
}
