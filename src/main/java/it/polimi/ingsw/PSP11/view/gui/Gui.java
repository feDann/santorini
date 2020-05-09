package it.polimi.ingsw.PSP11.view.gui;

import it.polimi.ingsw.PSP11.client.GUIClient;
import it.polimi.ingsw.PSP11.view.gui.controller.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {
    private Stage newStage;
    GUIClient client;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/initialScene.fxml"));
        Parent root = loader.load();
        GUIController controller = loader.getController();
        client = new GUIClient();
        controller.setClient(client);
        client.setController(controller);
        newStage = stage;
        newStage.setTitle("Santorini");
        newStage.setResizable(false);
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    @Override
    public void stop(){
        try {
            client.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}

