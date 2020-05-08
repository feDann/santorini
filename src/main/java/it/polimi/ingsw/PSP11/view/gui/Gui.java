package it.polimi.ingsw.PSP11.view.gui;

import it.polimi.ingsw.PSP11.view.gui.controller.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {
    private Stage newStage;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/initialScene.fxml"));
        Parent root = loader.load();
        GUIController controller = loader.getController();
        //networkhandler?
        newStage = stage;
        newStage.setTitle("Santorini");
        newStage.setResizable(false);
        newStage.setScene(new Scene(root));
        newStage.show();
    }
}

