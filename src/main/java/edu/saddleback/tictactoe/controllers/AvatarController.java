package edu.saddleback.tictactoe.controllers;

import java.io.IOException;
import java.net.URL;

import edu.saddleback.tictactoe.Game;
import edu.saddleback.tictactoe.models.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AvatarController {
    // fxml properties
    public HBox p1 = null;
    public HBox p2 = null;
    public AnchorPane error = null;
    public Button error_btn = null;
    public Text error_message = null;

    private HBox previousP1 = null;
    private HBox previousP2 = null;

    public AvatarController() {
        player1 = new Player("Carlos");
        player2 = new Player("Professor");
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
        URL avatarPath = Game.class.getResource("images/avatars/" + avatarStr + ".jpg");
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
        URL avatarPath = Game.class.getResource("images/avatars/" + avatarStr + ".jpg");
        player2.setAvatarPath(avatarPath);
    }

    @FXML
    public void switchToGameScreen(ActionEvent event) throws IOException {
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

        FXMLLoader loader = new FXMLLoader(Game.class.getResource("views/Game.fxml"));
        loader.setController(new GameController(player1, player2));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane pane = loader.<AnchorPane>load();
        stage.getScene().setRoot(pane);
    }

    private Player player1 = null;
    private Player player2 = null;
}
