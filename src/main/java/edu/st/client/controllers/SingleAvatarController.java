package edu.st.client.controllers;

import edu.st.client.models.Player;
import edu.st.client.services.FxService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
  private HBox previousP2 = null;
  public GridPane grid;
  public ImageView p2StartImage = null;

  public SingleAvatarController() {
    readAvatars();
    player = new Player("Player 1");
    cpu = new Player("CPU");
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
    // String avatarStr = tile.getId().substring(0, tile.getId().length() - 2);
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

    // FxService.switchViews("views/Game.fxml", new SingleGameController(player,
    // cpu));
    FxService.switchViews("views/Game.fxml", new GameController(player, cpu));
  }

  private void cpuGenerateAvatar() {
    Random random = new Random();
    // player.setAvatarPath(Main.class.getResource("images/avatars/Ryu.jpg"));

    // Default is Ryu if the avatar path is null. TEMPORARY
    if (player.getAvatarPath() == null) {
      player.setAvatarPath(Main.class.getResource("images/avatars/Ryu.jpg"));
    }

    String avatarName = player.getAvatarPath().getPath();
    String avatarStr = avatarName.substring(33, avatarName.length() - 4);
    avatars.remove(avatarStr);

    String randAvatar = avatars.get(random.nextInt(avatars.size()));
    URL avatarPath = Main.class.getResource("images/avatars/" + randAvatar + ".jpg");
    cpu.setAvatarPath(avatarPath);

    // try {
    // Thread.sleep(5000); // Freeze for 4 seconds
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // }
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
  private List<String> avatars = new ArrayList<>();
}
