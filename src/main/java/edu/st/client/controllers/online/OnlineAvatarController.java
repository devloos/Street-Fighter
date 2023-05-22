package edu.st.client.controllers.online;

import edu.st.client.models.Player;
import edu.st.client.services.FxService;
import edu.st.common.Util;
import edu.st.common.messages.Message;
import edu.st.common.messages.Packet;
import edu.st.common.messages.client.PlayerAvatarChange;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.UUID;

import edu.st.client.Main;
import edu.st.client.controllers.BaseController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class OnlineAvatarController extends BaseController {
  public AnchorPane error = null;
  public Button error_btn = null;
  public Button startGame = null;
  public HBox p1 = null;
  public HBox p2 = null;
  private HBox previousP1 = null;
  public ImageView p2StartImage = null;

  public OnlineAvatarController(String hostname, String playername, Socket socket, UUID gameId, boolean imHost) {
    this.host = new Player(hostname);
    this.player = new Player(playername);
    this.socket = socket;
    this.gameId = gameId;
    this.imHost = imHost;
    FxService.setMedia("audio/selection.mp3");
    FxService.playMedia();
  }

  @FXML
  public void initialize() {
    error_btn.setOnAction(event -> {
      error.setVisible(false);
    });

    startGame.setOnAction(event -> {
      readyUp();
    });
    startGame.setText("Ready up!");

    Thread thread = new Thread(
        () -> {
          try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (flag) {
              String m = input.readLine();

              if (m == null) {
                continue;
              }

              Packet<Message> packet = Util.deserialize(m);

              if (packet == null) {
                continue;
              }

              Message message = packet.getMessage();

              if (message.getType().contains("PlayerAvatarChange")) {
                Platform.runLater(() -> {
                  PlayerAvatarChange msg = (PlayerAvatarChange) message;

                  URL avatarSelected = Main.class.getResource("images/avatars/" + msg.getAvatarSelected() + ".jpg");

                  if (imHost) {
                    player.setAvatarPath(avatarSelected);
                  } else {
                    host.setAvatarPath(avatarSelected);
                  }

                  p2StartImage.setImage(new Image(avatarSelected.toString()));
                });
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    thread.start();

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

    Util.println(socket, new PlayerAvatarChange(avatarStr), gameId.toString());
  }

  public void readyUp() {

  }

  private Player host = null;
  private Player player = null;
  private Socket socket = null;
  private UUID gameId = null;
  private boolean flag = true;
  private boolean imHost;
}
