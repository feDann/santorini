package it.polimi.ingsw.PSP11.view.gui;

import it.polimi.ingsw.PSP11.client.GUIClient;
import it.polimi.ingsw.PSP11.view.gui.controller.GUIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Gui extends Application {
    private Stage newStage;
    GUIClient client;

    /**
     * Start the GUI and load the first scene
     * @param stage the primary stage for this application, onto which the application scene can be set
     * @throws Exception if an error occurred during the initialization of the scene
     */

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

    /**
     * This method is called when the application should stop, and provides a convenient place to prepare for application exit and close the client connection
     */
    @Override
    public void stop(){
        try {
            client.close();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}

