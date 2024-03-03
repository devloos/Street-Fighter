package edu.st.client.controllers.multiplayer;

import java.net.URL;

import edu.st.client.Main;
import edu.st.client.controllers.BaseController;
import edu.st.client.models.Player;
import edu.st.client.services.FxService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MultiplayerAvatarController extends BaseController {
  // fxml properties
  public HBox p1 = null;
  public HBox p2 = null;
  public AnchorPane error = null;
  public Button error_btn = null;
  public Text error_message = null;
  public TextField p1TextField = null;
  public TextField p2TextField = null;

  private HBox previousP1 = null;
  private HBox previousP2 = null;

  public MultiplayerAvatarController() {
    player1 = new Player("Player 1");
    player2 = new Player("Player 2");
    FxService.setMedia("audio/selection.mp3");
    FxService.playMedia();
  }

  @FXML
  public void initialize() {
    error_btn.setOnAction(event -> {
      error.setVisible(false);
    });
  }

  // will use setAvatarPath for player1
  @FXML
  public void player1SelectedAvatar(MouseEvent event) {
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
    String avatarStr = tile.getId().substring(0, tile.getId().length() - 2);
    URL avatarPath = Main.class.getResource("images/avatars/" + avatarStr + ".jpg");
    player1.setAvatarPath(avatarPath);
  }

  // will use setAvatarPath for player2
  @FXML
  public void player2SelectedAvatar(MouseEvent event) {
    Node node = event.getPickResult().getIntersectedNode();

    if (!(node instanceof ImageView)) {
      return;
    }

    HBox tile = (HBox) node.getParent();

    ImageView image = (ImageView) p2.getChildren().get(0);

    // should be false on initial
    if (previousP2 != null) {
      previousP2.getChildren().get(0).setVisible(true);
    }
    previousP2 = tile;

    ImageView tileImage = (ImageView) tile.getChildren().get(0);

    image.setImage(tileImage.getImage());
    previousP2.getChildren().get(0).setVisible(false);

    // set functionality for player name as well
    String avatarStr = tile.getId().substring(0, tile.getId().length() - 2);
    URL avatarPath = Main.class.getResource("images/avatars/" + avatarStr + ".jpg");
    player2.setAvatarPath(avatarPath);
  }

  @FXML
  public void switchToGameScreen(ActionEvent event) {
    if (player1.getAvatarPath() == null || player2.getAvatarPath() == null) {
      error_message.setText("PLEASE CHOOSE AN AVATAR!");
      error.setVisible(true);
      return;
    }

    if (player1.getAvatarPath().equals(player2.getAvatarPath())) {
      error_message.setText("PLEASE CHOOSE DIFFERENT AVATARS!");
      error.setVisible(true);
      return;
    }

    // Set player names that are currently in their respective text fields
    if (p1TextField.getText() != "") {
      player1.setName(p1TextField.getText());
    }

    if (p2TextField.getText() != "") {
      player2.setName(p2TextField.getText());
    }

    FxService.switchViews("views/Game.fxml", new MultiplayerGameController(player1, player2));
  }

  private Player player1 = null;
  private Player player2 = null;
}
