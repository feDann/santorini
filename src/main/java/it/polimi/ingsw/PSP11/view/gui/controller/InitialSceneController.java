package it.polimi.ingsw.PSP11.view.gui.controller;
import it.polimi.ingsw.PSP11.client.GUIClient;
import it.polimi.ingsw.PSP11.messages.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class InitialSceneController extends GUIController{

    @FXML
    private Pane initialPane;

    @FXML
    private TextField ip, nickname;

    @FXML
    private Button connection,playButton;

    @FXML
    private Text errorText,numOfPlayersText,errorText2,opponentText;

    @FXML
    private Group radioButtons;

    @FXML
    private RadioButton twoPlayers,threePlayers;

    @FXML
    private ToggleGroup numOfPlayers;


    @FXML
    public void initialize(){
        ip.setVisible(true);
        connection.setVisible(true);
        errorText.setVisible(true);
        playButton.setVisible(false);
        nickname.setVisible(false);
        numOfPlayersText.setVisible(false);
        errorText2.setVisible(false);
        radioButtons.setVisible(false);
        opponentText.setVisible(false);

    }


    @FXML
    public void sendIp(ActionEvent event) {
        Task task = new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                getClient().setIp(ip.getText());
                try{
                    getClient().start();
                    setupInfoScene();
                }catch (Exception e) {
                    errorText.setText("Invalid Ip!");
                }
                return null;
            }
        };
       new Thread(task).start();

    }

    @FXML
    public void sendNickname(ActionEvent event){
        Platform.runLater(()->{
            errorText2.setVisible(false);
            if(numOfPlayers.getSelectedToggle() != null ) {
                if(!nickname.getText().equals("")) {
                    String nick = nickname.getText();
                    getClient().asyncWrite(new NicknameMessage(nick));
                    setNickname(nick);
                }else{
                    nickname.setPromptText("Nickname can't be empty!");
                    nickname.setStyle("-fx-prompt-text-fill: indianred;");
                }
            }
            else{
                errorText2.setVisible(true);
            }
        });

    }

    private void setupInfoScene() {
        ip.setVisible(false);
        connection.setVisible(false);
        errorText.setVisible(false);
        playButton.setVisible(true);
        nickname.setVisible(true);
        numOfPlayersText.setVisible(true);
        radioButtons.setVisible(true);
        opponentText.setVisible(false);
    }

    private void waitOpponentScene(){
        ip.setVisible(false);
        connection.setVisible(false);
        errorText.setVisible(false);
        playButton.setVisible(false);
        nickname.setVisible(false);
        numOfPlayersText.setVisible(false);
        radioButtons.setVisible(false);
        errorText2.setVisible(false);
        opponentText.setVisible(true);


        Platform.runLater(() ->{
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, event -> {
                        String text = opponentText.getText();
                        opponentText.setText(
                                ("Wait for opponent . . .".equals(text))
                                        ? "Wait for opponent ."
                                        : text + " ."
                        );
                    }),
                    new KeyFrame(Duration.millis(500))
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }


    @Override
    public void changeStage() {
        Platform.runLater(() -> {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/selectGods.fxml"));
                Parent root = loader.load();
                GUIController newController = loader.getController();
                getClient().setController(newController);
                newController.setClient(getClient());
                Scene scene = new Scene(root);
                Stage newStage = (Stage) initialPane.getScene().getWindow();
                newStage.setScene(scene);
                newStage.show();

            }catch (Exception e){
                e.getStackTrace();
            }
        });
    }

    @FXML
    public void resetField(MouseEvent event){
        nickname.setText("");
        nickname.setPromptText("");
    }

    @Override
    public void handleMessage(Message message) {
        if(message instanceof DuplicateNicknameMessage){
            nickname.clear();
            nickname.setPromptText("Nickname already in use...");
            nickname.setStyle("-fx-prompt-text-fill: indianred;");
        }
        else if(message instanceof ConnectionMessage){
            String numOfPlayers = ((RadioButton)this.numOfPlayers.getSelectedToggle()).getText();
            getClient().asyncWrite(new PlayerSetupMessage(numOfPlayers));
        }
        else if(message instanceof WaitMessage){
            waitOpponentScene();
        }
        else if(message instanceof OpponentMessage){
            setOpponents(((OpponentMessage)message).getOpponents());
            setColor(((OpponentMessage) message).getColor());
            changeStage();
        }

    }

}
