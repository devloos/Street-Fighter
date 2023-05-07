package edu.st.client.services;

import java.io.IOException;

import edu.st.client.Main;
import edu.st.client.controllers.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FxService {
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
}
