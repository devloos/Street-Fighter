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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;


public class AvatarController {
    // fxml properties
    private Stage stage;
    private Scene scene;

    public HBox p1 = null;
    public HBox p2 = null;

    // will use setAvatarPath for player1
    @FXML
    public void selectPlayer1(MouseEvent event) {
        System.out.println("Selecting player 1");
    }

    // will use setAvatarPath for player2
    @FXML
    public void selectPlayer2(MouseEvent event) {
            System.out.println("Selecting player 2");
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