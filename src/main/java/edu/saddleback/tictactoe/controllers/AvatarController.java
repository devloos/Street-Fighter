package edu.saddleback.tictactoe.controllers;

import java.io.IOException;
import java.net.URL;

import edu.saddleback.tictactoe.Game;
import edu.saddleback.tictactoe.models.Player;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AvatarController {
    // fxml properties
    public HBox p1 = null;
    public HBox p2 = null;

    private HBox previousP1 = null;
    private HBox previousP2 = null;

    public AvatarController() {
        player1 = new Player("Carlos");
        player2 = new Player("Professor");
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
        AnchorPane sameAvatar = (AnchorPane) (((Node) event.getSource()).getScene().lookup("#sameAvatar"));
        AnchorPane noAvatar = (AnchorPane) (((Node) event.getSource()).getScene().lookup("#noAvatar"));
        Button return_no = (Button) (((Node) event.getSource()).getScene().lookup("#return_no"));
        Button return_same = (Button) (((Node) event.getSource()).getScene().lookup("#return_same"));
        GridPane grid1 = (GridPane) (((Node) event.getSource()).getScene().lookup("#grid1"));
        GridPane grid2 = (GridPane) (((Node) event.getSource()).getScene().lookup("#grid2"));
        HBox p1 = (HBox) (((Node) event.getSource()).getScene().lookup("#p1"));
        HBox p2 = (HBox) (((Node) event.getSource()).getScene().lookup("#p2"));
        
        sameAvatar.setVisible(false);
        noAvatar.setVisible(false);
        return_no.setVisible(false);
        return_same.setVisible(false);

        return_no.setOnAction(eventReturn -> {
            noAvatar.setVisible(false);
            return_no.setVisible(false);
            grid1.setVisible(true);
            grid2.setVisible(true);
            p1.setVisible(true);
            p2.setVisible(true);
        });

        return_same.setOnAction(eventReturn -> {
            sameAvatar.setVisible(false);
            return_same.setVisible(false);
            grid1.setVisible(true);
            grid2.setVisible(true);
            p1.setVisible(true);
            p2.setVisible(true);
        });

        if (player1.getAvatarPath() == null || player2.getAvatarPath() == null) {
            noAvatar.setVisible(true);
            return_no.setVisible(true);
            grid1.setVisible(false);
            grid2.setVisible(false);
            p1.setVisible(false);
            p2.setVisible(false);
            return;
        }

        if(player1.getAvatarPath().equals(player2.getAvatarPath())) {
            sameAvatar.setVisible(true);
            return_same.setVisible(true);
            grid1.setVisible(false);
            grid2.setVisible(false);
            p1.setVisible(false);
            p2.setVisible(false);
            return;
        }


        FXMLLoader loader = new FXMLLoader(Game.class.getResource("views/Game.fxml"));
        loader.setController(new GameController(player1, player2));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane pane = loader.<AnchorPane>load();
        stage.getScene().setRoot(pane);
    }

    // make function to verify if player info is null, if it is send an alert
    // will also send an alert if the player's URLs (avatar) is the same

    private Player player1 = null;
    private Player player2 = null;
}
