package edu.st.client.services;

import edu.st.client.models.Callback;
import edu.st.client.models.Player;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class GameService {
  public static void setPlayerProfile(AnchorPane profile, Player player) {
    ImageView image = (ImageView) profile.getChildren().get(0);
    image.setImage(new Image(player.getAvatarPath().toString()));

    AnchorPane inputDiv = (AnchorPane) profile.getChildren().get(1);
    Label label = (Label) inputDiv.getChildren().get(0);
    label.setText(player.getName());
  }

  public static void setPlayerTile(HBox tile, Player player) {
    ImageView imageTile = new ImageView(player.getAvatarPath().toString());
    imageTile.setFitWidth(100);
    imageTile.setFitHeight(100);
    imageTile.setEffect(new DropShadow());

    tile.getChildren().add(imageTile);

    tile.getStyleClass().removeAll("tile-active");
    tile.getStyleClass().add("tile-taken");
  }

  public static void resetTile(HBox tile) {
    if (tile.getStyleClass().contains("tile-active")) {
      return;
    }
    tile.getChildren().clear();
    tile.getStyleClass().removeAll("tile-taken");
    tile.getStyleClass().add("tile-active");
  }

  public static void setPlayerThinking(AnchorPane profile, boolean bool) {
    getIndicator(profile).setVisible(bool);
  }

  public static void addTask(int milli, Callback method) {
    Thread thread = new Thread(
        () -> {
          try {
            Thread.sleep(milli);
            Platform.runLater(() -> {
              method.call();
            });
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        });
    thread.start();
  }

  private static ProgressIndicator getIndicator(AnchorPane profile) {
    AnchorPane inputDiv = (AnchorPane) profile.getChildren().get(1);
    ProgressIndicator indicator = (ProgressIndicator) inputDiv.getChildren().get(1);
    return indicator;
  }
}
