package edu.st.client.controllers;

import edu.st.client.models.Player;
import edu.st.client.services.FxService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import edu.st.client.Main;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class SingleAvatarController extends BaseController {
  public TextField playerTextField = null;
  public AnchorPane error = null;
  public Button error_btn = null;
  public HBox p1 = null;
  public HBox p2 = null;
  private HBox previousP1 = null;
  public ImageView p2StartImage = null;

  public SingleAvatarController() {
    readAvatars();
    player = new Player("Player 1");
    cpu = new Player("CPU");
    // FxService.setMedia("audio/vs.mp3");
    // FxService.playMedia();
  }

  @FXML
  public void initialize() {
    error_btn.setOnAction(event -> {
      error.setVisible(false);
    });
  }

  @FXML
  public void playerSelectedAvatar(MouseEvent event) {
    Node node = event.getPickResult().getIntersectedNode();

    if (!(node instanceof ImageView)) {
      return;
    }

    HBox tile = (HBox) node.getParent();

    ImageView image = (ImageView) p1.getChildren().get(0);

    // should be false on initial
    if (previousP1 != null) {
      previousP1.getChildren().get(0).setVisible(true);
    }
    previousP1 = tile;

    ImageView tileImage = (ImageView) tile.getChildren().get(0);

    image.setImage(tileImage.getImage());
    previousP1.getChildren().get(0).setVisible(false);

    // set functionality for player name as well
    String avatarStr = tile.getId();
    URL avatarPath = Main.class.getResource("images/avatars/" + avatarStr +
        ".jpg");
    player.setAvatarPath(avatarPath);
  }

  @FXML
  public void switchToGameScreen(ActionEvent event) {
    // Set player names that are currently in their respective text fields
    if (playerTextField.getText() != "") {
      player.setName(playerTextField.getText());
    }

    cpuGenerateAvatar();

    Thread thread = new Thread(
        () -> {
          try {
            Thread.sleep(2000);
            Platform.runLater(() -> {
              FxService.switchViews("views/Game.fxml", new SingleGameController(player, cpu));
            });
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        });
    thread.start();
  }

  private void cpuGenerateAvatar() {
    Random random = new Random();

    if (player.getAvatarPath() == null) {
      player.setAvatarPath(Main.class.getResource("images/avatars/Ryu.jpg"));
    }

    String avatarFile = player.getAvatarPath().getPath();
    String fileName = new File(avatarFile).getName();
    String avatarStr = fileName.substring(0, fileName.lastIndexOf('.'));
    avatars.remove(avatarStr);

    String randAvatar = avatars.get(random.nextInt(avatars.size()));

    URL avatarPath = Main.class.getResource("images/avatars/" + randAvatar + ".jpg");
    cpu.setAvatarPath(avatarPath);

    p2StartImage.setImage(new Image(avatarPath.toString()));
  }

  private void readAvatars() {
    String filePath = (String) Main.class.getResource("data/avatars.db").getPath();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        avatars.add(line.trim());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Player player = null;
  private Player cpu = null;
  private ArrayList<String> avatars = new ArrayList<>();
}
