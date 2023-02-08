package edu.saddleback.tictactoe.controllers;

import java.io.IOException;
import java.net.URL;

import edu.saddleback.tictactoe.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class AvatarController {
    // fxml properties
    private Stage stage;
    private Scene scene;
    public HBox p1 = null;
    public HBox p2 = null;
    public HBox player1Space = null;
    public HBox player2Space = null;

    // will use setAvatarPath for player1
    @FXML
    public void selectPlayer1(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();

        if(!(node instanceof ImageView)) {
            return;
        }

        p1 = (HBox) node.getParent();

        String player1CharString = p1.getId();
        player1CharString = player1CharString.substring(0, player1CharString.length() - 2);

        URL url = Game.class.getResource("images/avatars/" + player1CharString + ".jpg");
        ImageView image = (ImageView) p1.getChildren().get(0);
        image.setImage(new Image(url.toString()));
        player1Space.getChildren().add(image);
        
    }

    // will use setAvatarPath for player2
    @FXML
    public void selectPlayer2(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();

        if(!(node instanceof ImageView)) {
            return;
        }

        p2 = (HBox) node.getParent();

        String player2CharString = p2.getId();
        player2CharString = player2CharString.substring(0, player2CharString.length() - 2);

        URL url = Game.class.getResource("images/avatars/" + player2CharString + ".jpg");
        ImageView image = (ImageView) p2.getChildren().get(0);
        image.setImage(new Image(url.toString()));
        player2Space.getChildren().add(image);
    }
    
    @FXML
    public void switchToGameScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Game.class.getResource("views/Game.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = loader.<Scene>load();
        stage.setScene(scene);
        stage.show();
    }
}