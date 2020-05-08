package it.polimi.ingsw.PSP11.view.gui.controller;
import it.polimi.ingsw.PSP11.client.GUIClient;
import it.polimi.ingsw.PSP11.messages.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class InitialSceneController extends GUIController{

    @FXML
    private Text errorText;

    @FXML
    private TextField ip;

    @FXML
    private Button connection;



    @FXML
    public void sendIp(ActionEvent event) {
        errorText.setText("");
        String ip = this.ip.getText();
        setClient(new GUIClient(ip));
        try{
            getClient().start();
            getClient().setController(this);
            changeScene();
        }catch (Exception e) {
            errorText.setText("Invalid Ip!");
        }

    }

    @Override
    public void changeScene() {

    }

    @Override
    public void handleMessage(Message message) {

    }
}
